package com.arademia.aqar.config.model;

import lombok.Data;

import java.util.List;

@Data
public class NewOrUpdateAlertRequest {
    private String title;
    private String body;
    private Integer compensation;
    private boolean isActive;
    private boolean isGeneric;
    private List<Integer> tagsIds;
}
