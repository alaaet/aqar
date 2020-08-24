package com.arademia.aqar.config.model;

import com.arademia.aqar.entity.User;
import lombok.Data;

@Data
public class GetUserResponse {
    private Integer id;
    private String title;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String role;

    public GetUserResponse(User usr){
            this.id = usr.getId();
            this.username=usr.getUsername();
            this.email = usr.getEmail();
            this.firstName = usr.getFirstName();
            this.lastName = usr.getLastName();
            this.role = usr.getRole().toString();
            this.title = usr.getTitle().toString();
    }
}
