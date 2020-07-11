package com.arademia.aqar.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "type")
public class ContactType {
    private Integer id;
    private String type;

    public ContactType(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    public ContactType() {
    }

    @Override
    public String toString() {
        return "ContactType{" +
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
