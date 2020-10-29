package com.arademia.aqar.config.model;

import com.arademia.aqar.entity.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuthenticationResponse {

    private Integer id;
    private String email;
    private String title;
    private String username;
    private String firstName;
    private String lastName;
    private String role;
    private String profileImage;
    private LocalDateTime dob;
    private String jwtToken;


    public AuthenticationResponse(User user, String jwt) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.title = user.getTitle().toString();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.role = user.getRole().toString();
        this.profileImage = user.getProfileImage();
        this.dob = user.getBirthday();
        this.jwtToken = jwt ;

    }

}
