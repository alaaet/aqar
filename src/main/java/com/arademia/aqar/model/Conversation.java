package com.arademia.aqar.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
@Data
@EqualsAndHashCode(of = {"senderId","RecipientId"})
public class Conversation {

    private Integer id, senderId, RecipientId;
    private LocalDateTime createdAt;

    public Conversation(Integer id, Integer senderId, Integer recipientId, LocalDateTime createdAt) {
        this.id = id;
        this.senderId = senderId;
        RecipientId = recipientId;
        this.createdAt = createdAt;
    }

    public Conversation() {
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "id=" + id +
                ", senderId=" + senderId +
                ", RecipientId=" + RecipientId +
                ", createdAt=" + createdAt +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Integer getRecipientId() {
        return RecipientId;
    }

    public void setRecipientId(Integer recipientId) {
        RecipientId = recipientId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
