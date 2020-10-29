package com.arademia.aqar.config.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NewOrUpdateTagRequest {
    private String value;
    private Integer material_id;
    private Integer dimension_id;
    private boolean isAssigned;
    private boolean isLost;
}
