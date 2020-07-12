package com.arademia.aqar.config.service;


import com.arademia.aqar.entity.Authority;
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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Get the User object
        com.arademia.aqar.entity.User user = userRepository.getUserByEmail(email);
        // Get Roles of the User
        List<Authority> rowAuthoritiesList = user.getAuthorities();
        List<String> roles = new ArrayList<String>();
        for (Authority auth:rowAuthoritiesList) {
            roles.add(auth.getValue());
        }
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role)));
        // Return the UserDetails object
        return new User(user.getEmail(),user.getPassword(), authorities);
    }
}
