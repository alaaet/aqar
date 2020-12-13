package com.arademia.aqar.config.model;

import com.arademia.aqar.entity.QrCode;
import com.arademia.aqar.entity.User;
import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class GetTagResponse {

    private Integer id;
    private Integer userId;
    private String materialType;
    private String dimensionType;
    private String value;
    private String activationCode;
    private List<Integer> alerts = new ArrayList<>();
    private LocalDateTime assignedAt;
    private LocalDateTime lostAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;


    public GetTagResponse(QrCode tag){
        this.id = tag.getId();
        this.userId = tag.getUserId();
        this.materialType = tag.getMaterialType().getMaterial();
        this.dimensionType = tag.getDimensionType().getName();
        this.value = tag.getValue();
        this.activationCode = tag.getActivationCode();
        tag.getAlerts().forEach(alert -> {this.alerts.add(alert.getId());});
        this.assignedAt = tag.getAssignedAt();
        this.lostAt = tag.getLostAt();
        this.createdAt = tag.getCreatedAt();
        this.updatedAt = tag.getUpdatedAt();
        this.deletedAt = tag.getDeletedAt();
    }
}
