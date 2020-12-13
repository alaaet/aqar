package com.arademia.aqar.config.model;

import com.arademia.aqar.entity.Alert;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class GetAlertResponse {
    private Integer id;
    private Integer userId;
    private String title;
    private String body;
    private Double compensation;
    private List<GetTagResponse> tags = new ArrayList<GetTagResponse>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public  GetAlertResponse(Alert alert){
    this.id = alert.getId();
    this.userId = alert.getUserId();
    this.title = alert.getTitle();
    this.body = alert.getBody();
    this.compensation = alert.getCompensation();
    alert.getTags().forEach(tag -> {this.tags.add(new GetTagResponse(tag));});
    this.createdAt = alert.getCreatedAt();
    this.updatedAt = alert.getUpdatedAt();
    this.deletedAt = alert.getDeletedAt();
    }
}
