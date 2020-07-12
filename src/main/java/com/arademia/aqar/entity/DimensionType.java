package com.arademia.aqar.entity;

import com.arademia.aqar.config.ConfigsConst;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = ConfigsConst.DIMENSION_TYPES)
@Data
@EqualsAndHashCode(of = "material")
public class DimensionType {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "height")
    private Double height;
    @Column(name = "width")
    private String width;
    @Column(name = "writable_chars")
    private String writableCharacters;
}
