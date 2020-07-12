package com.arademia.aqar.entity;

import com.arademia.aqar.config.ConfigsConst;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = ConfigsConst.ALERTS)
@Entity
@Data
@EqualsAndHashCode(of ={"userId", "body"})
public class Alert {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "compensation")
    private Double compensation;
    @Column(name = "body")
    private String body;
    @Column(name = "is_active")
    private Boolean isActive;

    // MANY TO MANY
    @ManyToMany
    @JoinTable(name = ConfigsConst.ALERT_QRS, joinColumns = {@JoinColumn(name = "alert_id")},inverseJoinColumns = {@JoinColumn(name = "qr_code_id")})
    private List<QrCode> qrCodes = new ArrayList<QrCode>();

    // DATETIME CONTROLS
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

}

