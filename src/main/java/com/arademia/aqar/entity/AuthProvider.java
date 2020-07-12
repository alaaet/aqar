package com.arademia.aqar.entity;

import com.arademia.aqar.config.ConfigsConst;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Table(name = ConfigsConst.AUTH_PROVIDERS)
@Entity
@Data
@EqualsAndHashCode(of = "socialId")
public class AuthProvider {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "social_id")
    private String socialId;
    @Column(name = "provider_name")
    private String providerName;

}

