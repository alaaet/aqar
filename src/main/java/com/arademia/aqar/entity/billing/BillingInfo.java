package com.arademia.aqar.entity.billing;

import com.arademia.aqar.config.ConfigsConst;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = ConfigsConst.BILLING_INFO)
@Entity
@Data
@EqualsAndHashCode(of ={"userId","data"})
public class BillingInfo {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @ManyToOne
    @JoinColumn(name = "billing_type", referencedColumnName = "id")
    private BillingType billingType;
    @Column(name = "billing_cycle")
    private Integer billingCycle;
    @Column(name = "data")
    private String data;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
