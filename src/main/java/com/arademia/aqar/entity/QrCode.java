package com.arademia.aqar.entity;

import com.arademia.aqar.config.ConfigsConst;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = ConfigsConst.QR_CODES)
@Entity
@Data
@EqualsAndHashCode(of ="")
public class QrCode {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;

    // MANY TO MANY
    @OneToMany(mappedBy = "qrCodes")
    private List<Alert> alerts = new ArrayList<Alert>();

    // MANY TO ONE
    @ManyToOne
    @JoinColumn(name = "material_id", referencedColumnName = "id")
    private MaterialType materialType;
    @ManyToOne
    @JoinColumn(name = "dimension_id", referencedColumnName = "id")
    private DimensionType dimensionType;

    // DATETIME CONTROLS
    @Column(name = "lost_at")
    private LocalDateTime lostAt;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
