package com.arademia.aqar.controller;

import com.arademia.aqar.config.model.AuthenticationRequest;
import com.arademia.aqar.config.model.AuthenticationResponse;
import com.arademia.aqar.config.service.MyUserDetailsService;
import com.arademia.aqar.config.util.JwtUtil;
import com.arademia.aqar.jdbc.dao.UserDao;
import com.arademia.aqar.model.User;
import com.arademia.aqar.util.Mappings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = Mappings.USERS)
@CrossOrigin
public class UserController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserDao userDao;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtTokenUtil;


    //////////////////// PUBLIC METHODS ////////////////////
    @RequestMapping(value = Mappings.AUTHENTICATE, method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword())
            );
        }catch (BadCredentialsException e){
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.UNAUTHORIZED);
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok( new AuthenticationResponse(jwt));
    }

    @PostMapping(value = Mappings.REGISTER)
    public ResponseEntity<User> registerUser(@RequestBody User user) throws URISyntaxException {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        final User createdUser = userDao.create(user);
        if (createdUser == null)
        {
            return ResponseEntity.notFound().build();
        }
        else
        {
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        }
    }

    //////////////////// USER METHODS ////////////////////
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(value="/{id}")
    public ResponseEntity<String> updateUser(@RequestBody User user) throws URISyntaxException{
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Boolean isUpdated = userDao.update(user);
        if (!isUpdated)
        {
            return ResponseEntity.notFound().build();
        }
        else
        {
            return ResponseEntity.ok("User ("+user.getFirstName()+" "+user.getLastName()+") was updated successfully!");
        }
    }



    //////////////////// USER/ADMIN METHODS ////////////////////
    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        final User user = userDao.getUser(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@RequestParam int id, HttpServletRequest request) throws URISyntaxException {
        Boolean isDeleted = userDao.delete(userDao.getUser(id));
        if (!isDeleted)
        {
            return ResponseEntity.notFound().build();
        }
        else
        {
            return ResponseEntity.ok("The user with ID ("+id+") was not deleted!");
        }
    }

    //////////////////// ADMIN METHODS ////////////////////
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user) throws URISyntaxException{
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        final User createdUser = userDao.create(user);
        if (createdUser == null) {
            return ResponseEntity.notFound().build();
        } else {
            URI uri = new URI(ServletUriComponentsBuilder.fromUri(new URI(ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()))
                    .path(Mappings.USERS).queryParam("id",createdUser.getId()).toUriString());
            return ResponseEntity.created(uri)
                    .body(createdUser);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userDao.getAllUsers(), HttpStatus.OK);
    }
}
