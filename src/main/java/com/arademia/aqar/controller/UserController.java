package com.arademia.aqar.controller;

import com.arademia.aqar.config.model.*;
import com.arademia.aqar.config.service.MyUserDetailsService;
import com.arademia.aqar.config.util.JwtUtil;
import com.arademia.aqar.entity.User;
import com.arademia.aqar.entity.constants.UserConstants;
import com.arademia.aqar.repos.UserRepository;
import com.arademia.aqar.util.Mappings;
import com.arademia.aqar.util.SupportingTools;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@Slf4j
@RestController
@RequestMapping(value = Mappings.USERS)
@CrossOrigin(origins = {"http://localhost:9000","http://167.86.81.129:8082","http://reftag.net"})
public class UserController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtTokenUtil;
    private static SupportingTools util = new SupportingTools();


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
            return ResponseEntity.badRequest().body(new ErrorResponse("Email or password is incorrect"));
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        final User user = userRepository.getUserByEmail(authenticationRequest.getUsername());
        AuthenticationResponse res = new AuthenticationResponse(user,jwt);
        return ResponseEntity.ok(res);
    }

    @PostMapping(value = Mappings.REGISTER)
    public ResponseEntity<?> registerUser(@RequestBody NewOrUpdateUserRequest rowUser) throws URISyntaxException {
        if(!util.isValidEmail(rowUser.getEmail()))
        {
            return ResponseEntity.badRequest().body(new ErrorResponse("Email field is not correct!"));
        }
        rowUser.setPassword(bCryptPasswordEncoder.encode(rowUser.getPassword()));
        try{
            final User createdUser = userRepository.save(new User(rowUser));
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        }
        catch (Exception e){
            if (e instanceof DataIntegrityViolationException){
                return ResponseEntity.badRequest().body(new ErrorResponse("User already exists, try to log in"));

            }
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @PostMapping(Mappings.REFRESH_TOKEN)
    public ResponseEntity<?> refreshAuthenticationToken(@RequestBody String row_jwt) throws Exception{
        if(row_jwt != null && !row_jwt.isEmpty() && !row_jwt.equals("null")){
            String jwt = row_jwt.replaceAll("^[\"']+|[\"']+$", "");
            String username = jwtTokenUtil.extractUsername(jwt);
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtTokenUtil.validateToken(jwt,userDetails)){
                final User user = userRepository.getUserByEmail(username);
                AuthenticationResponse res = new AuthenticationResponse(user,jwt);
                return ResponseEntity.ok(res);
            }
            else{
                return ResponseEntity.badRequest().body(new ErrorResponse("Please log in again"));
            }
        }
        else{
            return ResponseEntity.badRequest().body(new ErrorResponse("Please log in again"));
        }
    }

    //////////////////// USER METHODS ////////////////////






    //////////////////// USER/ADMIN METHODS ////////////////////
    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") int id) {
        final Optional<User> user = userRepository.findById(id);
        if (user == null) {
            return ResponseEntity.badRequest().body(new ErrorResponse("We could not fetch this user,please contact us"));
        } else {
            return ResponseEntity.ok(user);
        }
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
        if(rowUser.getPassword()!=null && !rowUser.getPassword().isEmpty() && rowUser.getPassword().equals(rowUser.getConfirmPassword())) user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        final User updatedUser = userRepository. save(user);
        UpdateUserResponse response = new UpdateUserResponse(updatedUser);
        if (updatedUser!= null) return ResponseEntity.ok(response);
        else return ResponseEntity.badRequest().body(new ErrorResponse("Update operation failed, please contact us"));
    }

    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") int id, HttpServletRequest request) throws URISyntaxException {
        GetUserResponse deletedUser = new GetUserResponse(userRepository.findById(id).get());
        long initCount = userRepository.count();
        userRepository.deleteById(id);
        if (initCount<= userRepository.count())
        {
            return ResponseEntity.badRequest().body(new ErrorResponse("User was not deleted, please contact us"));
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
        User user =  new User(rowUser);
        if(rowUser.getPassword().length()>0 && rowUser.getPassword().equals(rowUser.getConfirmPassword())) user.setPassword(bCryptPasswordEncoder.encode(rowUser.getPassword()));
        else return ResponseEntity.badRequest().body("passwords do not match!");
        final User createdUser = userRepository.save(user);
        if (createdUser == null) {
            return ResponseEntity.badRequest().body(new ErrorResponse("User was not saved, please contact us"));
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
