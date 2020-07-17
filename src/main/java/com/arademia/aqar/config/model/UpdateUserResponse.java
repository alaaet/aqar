package com.arademia.aqar.config.model;

import com.arademia.aqar.entity.User;
import lombok.Data;

@Data
public class UpdateUserResponse {
    private Integer id;
    private String email;
    private String title;
    private String firstName;
    private String lastName;
    private String role;

    public UpdateUserResponse(User updatedUser){
        this.id = updatedUser.getId();
        this.email = updatedUser.getEmail();
        this.title = updatedUser.getTitle().toString();
        this.firstName = updatedUser.getFirstName();
        this.lastName = updatedUser.getLastName();
        this.role = updatedUser.getRole().toString();
    }
}
