package com.arademia.aqar.config.model;

import lombok.Data;

@Data
public class NewCommentRequest {
    private String tagCode;
    private String sender;
    private String title;
    private String body;
    private String image;
    private Boolean isOwner;
}
