package com.arademia.aqar.entity;

import com.arademia.aqar.config.ConfigsConst;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = ConfigsConst.ADDRESSES)
@Entity
@Data
@Where(clause="! deleted_at is null")
@EqualsAndHashCode(of ={"firstLine","secondLine","city","region","country"})
public class Address {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "is_primary")
    private Boolean isPrimary;
    @Column(name = "first_name")
    private String firstLine;
    @Column(name = "second_line")
    private String secondLine;
    @Column(name = "city")
    private String  city;
    @Column(name = "region")
    private String region;
    @Column(name = "country")
    private String country;
    @Column(name = "postal_code")
    private String postalCode;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

}
