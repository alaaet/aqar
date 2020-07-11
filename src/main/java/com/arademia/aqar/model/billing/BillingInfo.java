package com.arademia.aqar.model.billing;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of ={"userId","data"})
public class BillingInfo {
    private Integer id, userId, billingType, billingCycle;
    private String data;
    private LocalDateTime createdAt, updatedAt, deletedAt;

    public BillingInfo(Integer id, Integer userId, Integer billingType, Integer billingCycle, String data, LocalDateTime createdAt,LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.billingType = billingType;
        this.billingCycle = billingCycle;
        this.data = data;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public BillingInfo() {
    }

    @Override
    public String toString() {
        return "BillingInfo{" +
                "id=" + id +
                ", userId=" + userId +
                ", billingType=" + billingType +
                ", billingCycle=" + billingCycle +
                ", data='" + data + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
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

    public Integer getBillingType() {
        return billingType;
    }

    public void setBillingType(Integer billingType) {
        this.billingType = billingType;
    }

    public Integer getBillingCycle() {
        return billingCycle;
    }

    public void setBillingCycle(Integer billingCycle) {
        this.billingCycle = billingCycle;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
