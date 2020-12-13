package com.arademia.aqar.entity;

import com.arademia.aqar.config.ConfigsConst;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = ConfigsConst.COMMENTS,uniqueConstraints = {@UniqueConstraint(columnNames = {"title","body"})})
@Entity
@Data
@Where(clause="! deleted_at is null")
@EqualsAndHashCode(of ={"title", "body"})
public class Comment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "qr_code_id")
    private Integer qrCodeId;
    @Column(name = "sender")
    private String sender;
    @Column(name = "title")
    private String title;
    @Column(name = "body")
    private String body;
    @Column(name = "image")
    private String image;
    @Column(name = "is_owner")
    private Boolean isOwner;


    // DATETIME CONTROLS
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Comment(){
        super();
        this.createdAt = this.updatedAt = LocalDateTime.now();
    }
}
