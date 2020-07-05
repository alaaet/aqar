package com.arademia.aqar.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = {"userId","value"})
public class Authority {

    private Integer userId;
    private String value;

    public Authority() {
    }

    public Authority(Integer userId, String value) {
        this.userId = userId;
        this.value = value;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
