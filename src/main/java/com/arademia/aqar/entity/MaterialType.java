package com.arademia.aqar.entity;

import com.arademia.aqar.config.ConfigsConst;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = ConfigsConst.MATERIAL_TYPES)
@Data
@EqualsAndHashCode(of = "material")
public class MaterialType {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "material")
    private String material;
    @Column(name = "is_waterproof")
    private Boolean isWaterproof;
}
