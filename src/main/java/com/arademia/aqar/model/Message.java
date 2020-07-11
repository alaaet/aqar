package com.arademia.aqar.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of = {"body","convId","msgType"})
public class Message {
    private Integer id,convId, msgType;
    private String body;
    private LocalDateTime createdAt,seenAt,deletedAt;

    public Message(Integer id, Integer convId, Integer msgType, String body, LocalDateTime createdAt) {
        this.id = id;
        this.convId = convId;
        this.msgType = msgType;
        this.body = body;
        this.createdAt = createdAt;
    }

    public Message() {
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", convId=" + convId +
                ", msgType=" + msgType +
                ", body='" + body + '\'' +
                ", createdAt=" + createdAt +
                ", seenAt=" + seenAt +
                ", deletedAt=" + deletedAt +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getConvId() {
        return convId;
    }

    public void setConvId(Integer convId) {
        this.convId = convId;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getSeenAt() {
        return seenAt;
    }

    public void setSeenAt(LocalDateTime seenAt) {
        this.seenAt = seenAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
