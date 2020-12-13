package com.arademia.aqar.controller;

import com.arademia.aqar.config.model.*;
import com.arademia.aqar.config.service.MyUserDetailsService;
import com.arademia.aqar.config.util.JwtUtil;
import com.arademia.aqar.entity.*;
import com.arademia.aqar.entity.constants.UserConstants;
import com.arademia.aqar.entity.util.ModelConverter;
import com.arademia.aqar.repos.*;
import com.arademia.aqar.storage.StorageService;
import com.arademia.aqar.util.Mappings;
import com.arademia.aqar.util.SupportingTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RestController
@RequestMapping(value = Mappings.USERS)
@CrossOrigin(origins = {"http://localhost:9000","http://167.86.81.129:8082","http://reftag.net","https://clavitag.com","https://www.clavitag.com"})
public class UserController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private AlertRepository alertRepo;
    @Autowired
    private AuthProviderRepository authProviderRepository;
    @Autowired
    private PublicProfileRepository ppRepo;
    @Autowired
    private ContactDetailRepository contactRepo;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtTokenUtil;
    private static SupportingTools util = new SupportingTools();
    @Autowired
    private ModelConverter converter;
    @Autowired
    private final StorageService storageService;

    public UserController(StorageService storageService) {
        this.storageService = storageService;
    }

    //////////////////// PUBLIC METHODS ////////////////////
    @RequestMapping(value = Mappings.AUTHENTICATE, method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        System.out.println("auth-request: "+authenticationRequest.getUsername()+"/"+authenticationRequest.getPassword());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword())
            );
        }catch (AuthenticationException e){
            System.out.println(e.getLocalizedMessage()+"/n"+e.getMessage());
            return ResponseEntity.badRequest().body(new ResponseMessage("Email or password is incorrect"));
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        final User user = userRepository.getUserByUsernameIgnoreCase(authenticationRequest.getUsername());
        AuthenticationResponse res = new AuthenticationResponse(user,jwt);
        return ResponseEntity.ok(res);
    }

    @RequestMapping(value = Mappings.SOCIAL_AUTHENTICATE, method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationTokenFromSocialAccount(@RequestBody SocialAuthenticationRequest authenticationRequest) throws Exception{
        System.out.println("auth-request: "+authenticationRequest.toString());
        AuthProvider socialAccount = new AuthProvider();
        UserDetails userDetails = userDetailsService.loadUserByEmail(authenticationRequest.getEmail());
        User usr;
        if(userDetails==null){
            // create native and social accounts
            User newUser = new User();
            newUser.setEmail(authenticationRequest.getEmail());
            newUser.setFirstName(authenticationRequest.getGiven_name());
            newUser.setLastName(authenticationRequest.getFamily_name());
            newUser.setProfileImage(authenticationRequest.getPicture());
            newUser.setUsername(authenticationRequest.getName());
            usr = userRepository.save(newUser);
            userDetails = userDetailsService.loadUserByEmail(usr.getEmail());
            // create social account
            socialAccount.setProviderName(authenticationRequest.getProvider());
            socialAccount.setSocialId(authenticationRequest.getSub());
            socialAccount.setUserId(usr.getId());
            authProviderRepository.save(socialAccount);
        }else{
            usr = userRepository.getUserByEmail(authenticationRequest.getEmail());
            if(usr.getProfileImage()==null )usr.setProfileImage(authenticationRequest.getPicture());
            // check if social account is attached
            AuthProvider retrievedSocialAccount = authProviderRepository.getSocialAccountBySocialId(authenticationRequest.getSub());
            if(retrievedSocialAccount == null){
                // create social account
                socialAccount.setProviderName(authenticationRequest.getProvider());
                socialAccount.setSocialId(authenticationRequest.getSub());
                socialAccount.setUserId(usr.getId());
                authProviderRepository.save(socialAccount);
            }
        }
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        AuthenticationResponse res = new AuthenticationResponse(usr,jwt);
        // Get Roles of the User
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + usr.getRole()));
        try {
            Authentication authentication=new UsernamePasswordAuthenticationToken(
                    userDetails,userDetails.getPassword(),authorities);
            //authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok(res);
        }catch (AuthenticationException e){
            System.out.println(e.getLocalizedMessage()+"/n"+e.getMessage());
            return ResponseEntity.badRequest().body(new ResponseMessage("Email or password is incorrect"));
        }
    }

    @PostMapping(value = Mappings.REGISTER)
    public ResponseEntity<?> registerUser(@RequestBody NewOrUpdateUserRequest rowUser) throws URISyntaxException {
        if(!util.isValidEmail(rowUser.getEmail()))
        {
            return ResponseEntity.badRequest().body(new ResponseMessage("Email field is not correct!"));
        }
        try{
            rowUser.setRole(UserConstants.Role.USER.toString());
            User tempUser = converter.toUser(rowUser);
            if(rowUser.getPassword()!=null && !rowUser.getPassword().isEmpty() && rowUser.getPassword().equals(rowUser.getConfirmPassword())) tempUser.setPassword(bCryptPasswordEncoder.encode(rowUser.getPassword()));
            final User createdUser = userRepository.save(tempUser);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        }
        catch (Exception e){
            if (e instanceof DataIntegrityViolationException){
                return ResponseEntity.badRequest().body(new ResponseMessage("User already exists, try to log in"));

            }
            return ResponseEntity.badRequest().body(new ResponseMessage(e.getMessage()));
        }
    }

    @PostMapping(Mappings.REFRESH_TOKEN)
    public ResponseEntity<?> refreshAuthenticationToken(@RequestBody String row_jwt) throws Exception{
        if(row_jwt != null && !row_jwt.isEmpty() && !row_jwt.equals("null")){
            String jwt = row_jwt.replaceAll("^[\"']+|[\"']+$", "");
            String username = jwtTokenUtil.extractUsername(jwt).toString();
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtTokenUtil.validateToken(jwt,userDetails)){
                final User user = userRepository.getUserByEmail(username);
                AuthenticationResponse res = new AuthenticationResponse(user,jwt);
                return ResponseEntity.ok(res);
            }
            else{
                return ResponseEntity.badRequest().body(new ResponseMessage("Please log in again"));
            }
        }
        else{
            return ResponseEntity.badRequest().body(new ResponseMessage("Please log in again"));
        }
    }

    @GetMapping(Mappings.BY_TAG_CODE+"/{tagCode}")
    public ResponseEntity<?> getPublicProfileByTagCode(@PathVariable("tagCode") String tagCode) {
        try {
            QrCode tag = tagRepository.getTagsByValue(tagCode).get(0);
            if (tag.getAssignedAt()==null) return ResponseEntity.badRequest().body(new ResponseMessage("tagIsNotAssigned"));
            try{
                PublicProfile publicProfile= ppRepo.getPublicProfileByUserId(tag.getUserId());
                publicProfile.setContactDetails(contactRepo.getContactDetailsByPublicProfileId(publicProfile.getId()));
                GetPublicDataResponse res = new GetPublicDataResponse(publicProfile);
                List<GetAlertResponse> alerts = new ArrayList<>();
                alertRepo.getAlertsByUserId(res.getUserId()).forEach(alert -> {
                    if(alert.getTags().size()==0||alert.getTags().contains(tag))
                    {
                        alerts.add(new GetAlertResponse(alert));
                    }
                });
                res.setAlerts(alerts);
                return ResponseEntity.ok(res);
            }
            catch (Exception e){
                return ResponseEntity.badRequest().body(new ResponseMessage("We could not fetch this user,please contact us"));
            }
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(new ResponseMessage("tagDoesNotExist"));
        }
    }

    //////////////////// USER METHODS ////////////////////
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(value = Mappings.UPDATE_PUBLIC_PROFILE)
    public ResponseEntity<?> updatePublicProfile(@RequestBody PublicProfile pp,HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        String email = principal.getName();
        User usr = userRepository.getUserByEmail(email);
        pp.setUser(usr);
        for (ContactDetail cnt:pp.getContactDetails()) {
            contactRepo.save(cnt);
        }
        ppRepo.save(pp);
        return ResponseEntity.ok(new ResponseMessage("Your public profile has been updated!"));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = Mappings.GET_PUBLIC_PROFILE)
    public ResponseEntity<?> getPublicProfile(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        String email = principal.getName();
        User usr = userRepository.getUserByEmail(email);
        PublicProfile pp = ppRepo.getPublicProfileByUserId(usr.getId());
        pp.setContactDetails(contactRepo.getContactDetailsByPublicProfileId(pp.getId()));
        GetPublicDataResponse res = new GetPublicDataResponse(pp);
        return ResponseEntity.ok(res);
    }



    //////////////////// USER/ADMIN METHODS ////////////////////
    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") int id) {
        final Optional<User> user = userRepository.findById(id);
        if (user == null) {
            return ResponseEntity.badRequest().body(new ResponseMessage("We could not fetch this user,please contact us"));
        } else {
            return ResponseEntity.ok(user);
        }
    }

    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    @PostMapping(value = Mappings.UPLOAD_PROFILE_PICTURE)
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file,HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        String email = principal.getName();
        User usr = userRepository.getUserByEmail(email);
        storageService.store(file);
        String filePath = storageService.load(file.getOriginalFilename()).toString();
        if(!filePath.isEmpty()){
            String[] path = filePath.split("webapps");
            String ip = util.getIp();
            if(ip!=null) {
                usr.setProfileImage("http://"+util.getIp()+":8080"+path[1]);
                userRepository.save(usr);
                return ResponseEntity.ok(new ResponseMessage("http://"+util.getIp()+":8080"+path[1]));
            }
        }
        return ResponseEntity.badRequest().body(new ResponseMessage("We could not  update the profile picture,please contact us"));
    }


    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{id}", method = {RequestMethod.POST,RequestMethod.PUT})
    public ResponseEntity<?> updateUser(@PathVariable("id") int id, @RequestBody NewOrUpdateUserRequest rowUser) throws URISyntaxException{
        User user =  userRepository.findById(id).get();
        user.setTitle(UserConstants.Title.valueOf(rowUser.getTitle()));
        user.setFirstName(rowUser.getFirstName());
        user.setLastName(rowUser.getLastName());
        user.setEmail(rowUser.getEmail());
        if(rowUser.getRole()!=null && !rowUser.getRole().isEmpty()) user.setRole(UserConstants.Role.valueOf(rowUser.getRole()));
        if(rowUser.getPassword()!=null && !rowUser.getPassword().isEmpty() && rowUser.getPassword().equals(rowUser.getConfirmPassword())) user.setPassword(bCryptPasswordEncoder.encode(rowUser.getPassword()));
        final User updatedUser = userRepository. save(user);
        UpdateUserResponse response = new UpdateUserResponse(updatedUser);
        if (updatedUser!= null) return ResponseEntity.ok(response);
        else return ResponseEntity.badRequest().body(new ResponseMessage("Update operation failed, please contact us"));
    }

    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") int id, HttpServletRequest request) throws URISyntaxException {
        User usr = userRepository.findById(id).get();
        GetUserResponse deletedUser = new GetUserResponse(usr);
        long initCount = userRepository.count();
        usr.setDeletedAt(LocalDateTime.now());
        try {
            userRepository.save(usr);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ResponseMessage(e.getMessage()));
        }

        if (initCount<= userRepository.count())
        {
            return ResponseEntity.badRequest().body(new ResponseMessage("User was not deleted, please contact us"));
        }
        else
        {
            return ResponseEntity.ok(deletedUser);
        }
    }

    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    @RequestMapping(value = Mappings.REVOKE_TOKEN, method = RequestMethod.POST)
    public ResponseEntity<Boolean> revokeToken (){
        return ResponseEntity.ok(true);
    }
    //////////////////// ADMIN METHODS ////////////////////
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody NewOrUpdateUserRequest rowUser) throws URISyntaxException{
        User user =  converter.toUser(rowUser);
        if(rowUser.getPassword().length()>0 && rowUser.getPassword().equals(rowUser.getConfirmPassword())) user.setPassword(bCryptPasswordEncoder.encode(rowUser.getPassword()));
        else return ResponseEntity.badRequest().body("passwords do not match!");
        final User createdUser = userRepository.save(user);
        if (createdUser == null) {
            return ResponseEntity.badRequest().body(new ResponseMessage("User was not saved, please contact us"));
        } else {
            URI uri = new URI(ServletUriComponentsBuilder.fromUri(new URI(ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()))
                    .path(Mappings.USERS).queryParam("id",createdUser.getId()).toUriString());
            return ResponseEntity.created(uri)
                    .body(createdUser);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<GetUserResponse>> getUsers() {
        List<User> rowUsers = (List<User>)userRepository.findAll();
        List<GetUserResponse> users = new ArrayList<>();
        for (User usr:rowUsers) {
            GetUserResponse tempUsr = new GetUserResponse(usr);
            users.add(tempUsr);
        }
        return  ResponseEntity.ok(users);
    }

}
