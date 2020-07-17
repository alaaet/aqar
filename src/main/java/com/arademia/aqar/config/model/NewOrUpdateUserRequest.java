package com.arademia.aqar.config.model;

import lombok.Data;

@Data
public class NewOrUpdateUserRequest {
    private String title;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String password;
    private String confirmPassword;
}
