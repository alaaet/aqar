package com.arademia.aqar.config.model;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String username;
    private String password;
}
