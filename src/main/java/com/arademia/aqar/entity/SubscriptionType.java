package com.arademia.aqar.entity;

import com.arademia.aqar.config.ConfigsConst;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = ConfigsConst.SUBSCRIPTION_TYPES)
@Data
@EqualsAndHashCode(of ="type")
public class   SubscriptionType {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "type")
    private String type;
    @Column(name = "monthly_cst")
    private Integer monthlyCost;
    @Column(name = "biannual_cst")
    private Integer biannualCost;
    @Column(name = "annual_cst")
    private Integer annualCost;


}
