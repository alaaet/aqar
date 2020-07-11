package com.arademia.aqar.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of ={"userId,subscriptionType"})
public class Subscription {
    private Integer id,userId,subscriptionType,billingCycle;
    private Boolean autoRenew;
    private LocalDateTime activatedAt,nextBillAt,StoppedAt;

    public Subscription(Integer id, Integer userId, Integer subscriptionType, Integer billingCycle, Boolean autoRenew, LocalDateTime activatedAt, LocalDateTime nextBillAt) {
        this.id = id;
        this.userId = userId;
        this.subscriptionType = subscriptionType;
        this.billingCycle = billingCycle;
        this.autoRenew = autoRenew;
        this.activatedAt = activatedAt;
        this.nextBillAt = nextBillAt;
    }

    public Subscription() {

    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", userId=" + userId +
                ", subscriptionType=" + subscriptionType +
                ", billingCycle=" + billingCycle +
                ", autoRenew=" + autoRenew +
                ", activatedAt=" + activatedAt +
                ", nextBillAt=" + nextBillAt +
                ", StoppedAt=" + StoppedAt +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(Integer subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public Integer getBillingCycle() {
        return billingCycle;
    }

    public void setBillingCycle(Integer billingCycle) {
        this.billingCycle = billingCycle;
    }

    public Boolean getAutoRenew() {
        return autoRenew;
    }

    public void setAutoRenew(Boolean autoRenew) {
        this.autoRenew = autoRenew;
    }

    public LocalDateTime getActivatedAt() {
        return activatedAt;
    }

    public void setActivatedAt(LocalDateTime activatedAt) {
        this.activatedAt = activatedAt;
    }

    public LocalDateTime getNextBillAt() {
        return nextBillAt;
    }

    public void setNextBillAt(LocalDateTime nextBillAt) {
        this.nextBillAt = nextBillAt;
    }

    public LocalDateTime getStoppedAt() {
        return StoppedAt;
    }

    public void setStoppedAt(LocalDateTime stoppedAt) {
        StoppedAt = stoppedAt;
    }
}
