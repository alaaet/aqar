package com.arademia.aqar.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = {"userId","typeId","value"})
public class ContactDetail {

    private Integer id,userId,typeId;
    private String value;
    private Boolean isPublic;

    public ContactDetail(Integer id, Integer userId, Integer typeId, String value, Boolean isPublic) {
        this.id = id;
        this.userId = userId;
        this.typeId = typeId;
        this.value = value;
        this.isPublic = isPublic;
    }

    public ContactDetail() {
    }

    @Override
    public String toString() {
        return "ContactDetail{" +
                "id=" + id +
                ", userId=" + userId +
                ", typeId=" + typeId +
                ", value='" + value + '\'' +
                ", isPublic=" + isPublic +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }
}
