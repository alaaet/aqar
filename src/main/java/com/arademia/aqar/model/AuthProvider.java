package com.arademia.aqar.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "socialId")
public class AuthProvider {
    private Integer userId;
    private String socialId, providerName;

    public AuthProvider(Integer userId, String socialId, String providerName) {
        this.userId = userId;
        this.socialId = socialId;
        this.providerName = providerName;
    }

    public AuthProvider() {
    }

    @Override
    public String toString() {
        return "AuthProvider{" +
                "id=" + userId +
                ", socialId='" + socialId + '\'' +
                ", providerName='" + providerName + '\'' +
                '}';
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }
}

