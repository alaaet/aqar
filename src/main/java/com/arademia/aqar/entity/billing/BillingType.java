package com.arademia.aqar.entity.billing;

import com.arademia.aqar.config.ConfigsConst;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Table(name = ConfigsConst.BILLING_TYPES)
@Entity
@Data
@EqualsAndHashCode(of ="type")
public class BillingType {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "type")
    private String type;


}
