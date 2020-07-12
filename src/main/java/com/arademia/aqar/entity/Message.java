package com.arademia.aqar.entity;

import com.arademia.aqar.config.ConfigsConst;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = ConfigsConst.MESSAGES)
@Data
@EqualsAndHashCode(of = {"body","convId","msgType"})
public class Message {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "conv_id")
    private Integer conversationId;
    @ManyToOne
    @JoinColumn(name = "msg_type", referencedColumnName = "id")
    private MsgType messageType;
    @Column(name = "body")
    private String body;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "seen_at")
    private LocalDateTime seenAt;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;


}
