package com.arademia.aqar.entity;

import com.arademia.aqar.config.ConfigsConst;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = ConfigsConst.SUBSCRIPTIONS)
@Data
@EqualsAndHashCode(of ={"userId,subscriptionType"})
public class Subscription {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "billing_cycle")
    private Integer billingCycle;
    @Column(name = "auto_renew")
    private Boolean autoRenew;

    // Many TO ONE
    @ManyToOne
    @JoinColumn(name = "sub_type", referencedColumnName = "id")
    private SubscriptionType subscriptionType;

    // ONE TO MANY
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "subscription_id")
    private List<Invoice> invoices = new ArrayList<Invoice>();

    // ONE TO ONE
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    // DATE TIME CONTROLS
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
