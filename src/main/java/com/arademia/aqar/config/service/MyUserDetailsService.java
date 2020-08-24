package com.arademia.aqar.config.service;


import com.arademia.aqar.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository  userRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Get the User object
        com.arademia.aqar.entity.User user = userRepository.getUserByUsername(username);
        // Get Roles of the User
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        // Return the UserDetails object
        return new User(user.getEmail(),user.getPassword(), authorities);
    }
    @Transactional
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        // Get the User object
         com.arademia.aqar.entity.User user = userRepository.getUserByEmail(email);
        if (user==null) return null;
        // Get Roles of the User
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        // Return the UserDetails object
        String pass = user.getPassword();
        if (pass==null || pass.isEmpty()) pass = "test";
        return new User(user.getEmail(),pass, authorities);
    }
}
