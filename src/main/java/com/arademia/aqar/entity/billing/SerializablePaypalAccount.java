package com.arademia.aqar.entity.billing;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of ="email")
public class SerializablePaypalAccount {
    private String email;

    public SerializablePaypalAccount(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "SerializablePaypalAccount{" +
                "email='" + email + '\'' +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
