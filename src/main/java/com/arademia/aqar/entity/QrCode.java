package com.arademia.aqar.entity;

import com.arademia.aqar.config.ConfigsConst;
import com.arademia.aqar.config.model.NewOrUpdateTagRequest;
import com.arademia.aqar.entity.billing.BillingInfo;
import com.arademia.aqar.entity.constants.UserConstants;
import com.arademia.aqar.repos.MaterialTypeRepository;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Where;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = ConfigsConst.QR_CODES,uniqueConstraints = {@UniqueConstraint(columnNames = {"id","activation_code"})})
@Entity
@Data
@Where(clause="! deleted_at is null")
@EqualsAndHashCode(of ="value")
public class QrCode {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "value")
    private String value;
    @Column(name = "activation_code")
    private String activationCode;

    // ONE TO MANY
    @Transient
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "qr_code_id")
    private List<Comment> comments = new ArrayList<Comment>();

    // MANY TO MANY
    @ManyToMany(mappedBy = "tags")
    private List<Alert> alerts = new ArrayList<Alert>();

    // MANY TO ONE
    @ManyToOne
    @JoinColumn(name = "material_id", referencedColumnName = "id")
    private MaterialType materialType;
    @ManyToOne
    @JoinColumn(name = "dimension_id", referencedColumnName = "id")
    private DimensionType dimensionType;

    // DATETIME CONTROLS
    @Column(name = "assigned_at")
    private LocalDateTime assignedAt;
    @Column(name = "lost_at")
    private LocalDateTime lostAt;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public QrCode() {
        super();
        this.createdAt = this.updatedAt = LocalDateTime.now();
    }


}
