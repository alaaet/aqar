package com.arademia.aqar.model.billing;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of="")
public class SerializableCreditCard {
    private String cardNumber, holderName, ccv;
    private LocalDate expiryDate;

    public SerializableCreditCard(String cardNumber, String holderName, String ccv, LocalDate expiryDate) {
        this.cardNumber = cardNumber;
        this.holderName = holderName;
        this.ccv = ccv;
        this.expiryDate = expiryDate;
    }

    public SerializableCreditCard() {

    }

    @Override
    public String toString() {
        return "SerializableCreditCard{" +
                "cardNumber='" + cardNumber + '\'' +
                ", holderName='" + holderName + '\'' +
                ", ccv='" + ccv + '\'' +
                ", expiryDate=" + expiryDate +
                '}';
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getCcv() {
        return ccv;
    }

    public void setCcv(String ccv) {
        this.ccv = ccv;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
}
