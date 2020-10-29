package com.arademia.aqar.entity;

import com.arademia.aqar.config.ConfigsConst;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = ConfigsConst.CONTACT_DETAILS)
@Data
@EqualsAndHashCode(of = {"ppId","type","value"})
public class ContactDetail {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "pp_id")
    private Integer publicProfileId;
    @Column(name = "type")
    private String type;
    @Column(name = "value")
    private String value;

}
