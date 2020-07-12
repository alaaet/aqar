package com.arademia.aqar.entity;

import com.arademia.aqar.config.ConfigsConst;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = ConfigsConst.CONTACT_DETAILS)
@Data
@EqualsAndHashCode(of = {"userId","typeId","value"})
public class ContactDetail {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private ContactType type;
    @Column(name = "value")
    private String value;
    @Column(name = "is_public")
    private Boolean isPublic;

}
