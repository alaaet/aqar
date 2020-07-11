package com.arademia.aqar.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of ={"subscriptionId","amount","createdAt"})
public class Invoice {
    private Integer id, subscriptionId;
    private Double amount;
    private LocalDateTime createdAt;

    public Invoice(Integer id, Integer subscriptionId, Double amount, LocalDateTime createdAt) {
        this.id = id;
        this.subscriptionId = subscriptionId;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public Invoice() {
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", subscriptionId=" + subscriptionId +
                ", amount=" + amount +
                ", createdAt=" + createdAt +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Integer subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
