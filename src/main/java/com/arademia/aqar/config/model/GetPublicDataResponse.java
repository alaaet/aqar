package com.arademia.aqar.config.model;

import com.arademia.aqar.entity.ContactDetail;
import com.arademia.aqar.entity.PublicProfile;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class
GetPublicDataResponse {
    private Integer id;
    private Integer userId;
    private String name;
    private String email;
    private Boolean enableProfilePicture;
    private String profilePicture;
    private List<ContactDetail> contactDetails;
    private List<GetAlertResponse> alerts = new ArrayList<>();

    public GetPublicDataResponse(PublicProfile pp){
        this.id = pp.getId();
        this.userId = pp.getUser().getId();
        this.name = pp.getName();
        this.email = pp.getEmail();
        this.enableProfilePicture = pp.getEnableProfilePicture();
        this.profilePicture = this.enableProfilePicture?pp.getUser().getProfileImage():"";
        this.contactDetails = pp.getContactDetails();
    }
}
