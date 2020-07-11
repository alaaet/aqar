package com.arademia.aqar.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "type")
public class MsgType {
    private Integer id;
    private String type;

    public MsgType(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    public MsgType() {
    }

    @Override
    public String toString() {
        return "MsgType{" +
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
