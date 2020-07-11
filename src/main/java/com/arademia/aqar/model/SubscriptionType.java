package com.arademia.aqar.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of ="type")
public class   SubscriptionType {
    private Integer id, monthlyCost,biannualCost,annualCost;
    private String type;

    public SubscriptionType(Integer id, Integer monthlyCost, Integer biannualCost, Integer annualCost, String type) {
        this.id = id;
        this.monthlyCost = monthlyCost;
        this.biannualCost = biannualCost;
        this.annualCost = annualCost;
        this.type = type;
    }

    public SubscriptionType() {
    }

    @Override
    public String toString() {
        return "SubscriptionType{" +
                "id=" + id +
                ", monthlyCost=" + monthlyCost +
                ", biannualCost=" + biannualCost +
                ", annualCost=" + annualCost +
                ", type='" + type + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMonthlyCost() {
        return monthlyCost;
    }

    public void setMonthlyCost(Integer monthlyCost) {
        this.monthlyCost = monthlyCost;
    }

    public Integer getBiannualCost() {
        return biannualCost;
    }

    public void setBiannualCost(Integer biannualCost) {
        this.biannualCost = biannualCost;
    }

    public Integer getAnnualCost() {
        return annualCost;
    }

    public void setAnnualCost(Integer annualCost) {
        this.annualCost = annualCost;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
