package com.arademia.aqar.config.model;

import com.arademia.aqar.entity.User;
import lombok.Data;

@Data
public class AuthenticationResponse {

    private Integer id;
    private String email;
    private String title;
    private String firstName;
    private String lastName;
    private String role;
    private String jwtToken;

    public AuthenticationResponse(User user, String jwt) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.title = user.getTitle().toString();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.role = user.getRole().toString();
        this.jwtToken = jwt ;
    }

}
