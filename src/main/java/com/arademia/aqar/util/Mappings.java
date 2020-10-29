package com.arademia.aqar.util;

public final class Mappings {
    // == constants ==


    // USERS
    public static final String  USERS = "/users";
    public static final String  BY_TAG_CODE = "/by-tag-code";
    public static final String  AUTHENTICATE = "/authenticate";
    public static final String  SOCIAL_AUTHENTICATE = "/social-authenticate";
    public static final String  REGISTER = "/register";
    public static final String  REVOKE_TOKEN = "/revoke-token";
    public static final String  REFRESH_TOKEN = "/refresh-token";
    public static final String UPLOAD_PROFILE_PICTURE = "upload-profile-picture";
    public static final String UPDATE_PUBLIC_PROFILE = "update-public-profile";
    public static final String GET_PUBLIC_PROFILE = "get-public-profile";

    // FILES
    public static final String  FILES = "/files";

    // TAGS
    public static final String  TAGS = "/tags";
    public static final String  ACTIVATE = "/activate";
    public static final String BY_USER = "/by-user";
    public static final String MATERIAL_TYPES = "/material-types";
    public static final String DIMENSION_TYPES = "/dimension-types";
    public static final String GENERATE_ACTIVATION_CODE = "/gen-act-code";



    // == constructors ==
    // private: because there is no need to instantiate it.
    private Mappings() {
    }
}
