package com.arademia.aqar.entity;

import com.arademia.aqar.config.ConfigsConst;
import com.arademia.aqar.entity.constants.UserConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Table(name = ConfigsConst.PUBLIC_PROFILES)
@Entity
@Where(clause="! deleted_at is null")
@EqualsAndHashCode(of = "id")
public class PublicProfile {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "enable_profile_picture")
    private Boolean enableProfilePicture;

    // DATE TIME CONTROLS
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // ONE TO MANY
    @Transient
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pp_id")
    private List<ContactDetail> contactDetails = new ArrayList<ContactDetail>();

    // ONE TO ONE
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


    // CONSTRUCTORS
    public PublicProfile(String name, String email, Boolean enableProfilePicture) {
        this.name = name;
        this.email = email;
        this.enableProfilePicture = enableProfilePicture;
        this.createdAt = this.updatedAt = LocalDateTime.now();
    }
    public PublicProfile() {
        super();
        this.createdAt = this.updatedAt = LocalDateTime.now();
    }
}
