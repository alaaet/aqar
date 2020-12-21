package com.arademia.aqar.entity;

import com.arademia.aqar.config.ConfigsConst;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = ConfigsConst.ALERTS,uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
@Entity
@Data
@Where(clause="! deleted_at is null")
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
    @Column(name = "title")
    private String title;
    @Column(name = "body")
    private String body;

    // MANY TO MANY
    @ManyToMany
    @JoinTable(name = ConfigsConst.ALERT_QRS, joinColumns = {@JoinColumn(name = "alert_id")},inverseJoinColumns = {@JoinColumn(name = "qr_code_id")})
    private List<QrCode> tags = new ArrayList<>();

    // DATETIME CONTROLS
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;


    public Alert() {
        super();
        this.createdAt = this.updatedAt = LocalDateTime.now();
    }

}

