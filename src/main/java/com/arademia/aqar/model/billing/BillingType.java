package com.arademia.aqar.model.billing;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of ="type")
public class BillingType {
    private Integer id;
    private String type;

    public BillingType(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    public BillingType() {
    }

    @Override
    public String toString() {
        return "BillingType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
