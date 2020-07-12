package com.arademia.aqar.entity;

import com.arademia.aqar.config.ConfigsConst;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = ConfigsConst.INVOICES)
@Data
@EqualsAndHashCode(of ={"subscriptionId","amount","createdAt"})
public class Invoice {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "subscription_id")
    private Integer subscriptionId;
    @Column(name = "amount")
    private Double amount;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
