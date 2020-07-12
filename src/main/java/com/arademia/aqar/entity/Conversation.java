package com.arademia.aqar.entity;

import com.arademia.aqar.config.ConfigsConst;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = ConfigsConst.CONVERSATIONS)
@Data
@EqualsAndHashCode(of = {"senderId","RecipientId"})
public class Conversation {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private User sender;
    @ManyToOne
    @JoinColumn(name = "recipient_id", referencedColumnName = "id")
    private User recipient;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "conv_id")
    private List<Message> messages = new ArrayList<Message>();
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
