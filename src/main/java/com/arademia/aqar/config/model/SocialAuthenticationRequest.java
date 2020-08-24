package com.arademia.aqar.config.model;

import lombok.Data;

@Data
public class SocialAuthenticationRequest {
    private String provider;
    private String sub;
    private String email;
    private String name;
    private String picture;
    private String given_name;
    private String family_name;
}
