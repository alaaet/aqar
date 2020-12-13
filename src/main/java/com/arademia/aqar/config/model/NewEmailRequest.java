package com.arademia.aqar.config.model;

import lombok.Data;
@Data
public class NewEmailRequest {
    private String recipient;
    private String subject;
    private String body;
}
