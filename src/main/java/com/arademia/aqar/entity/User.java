package com.arademia.aqar.entity;

import com.arademia.aqar.config.ConfigsConst;
import com.arademia.aqar.config.model.NewOrUpdateUserRequest;
import com.arademia.aqar.entity.billing.BillingInfo;
import com.arademia.aqar.entity.constants.UserConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Table(name = ConfigsConst.USERS,uniqueConstraints = {@UniqueConstraint(columnNames = {"email","username"})})
@Entity
@Where(clause="! deleted_at is null")
@EqualsAndHashCode(of = "id")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "credit")
    private Double credit;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "role")
    private UserConstants.Role role;
    @Column(name = "password")
    private String password;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "title")
    private UserConstants.Title title;
    @Column(name = "is_verified")
    private Boolean isVerified;
    @Column(name = "img_url")
    private String profileImage;
    
    // ONE TO MANY
    @Transient
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<BillingInfo> billingInfo = new ArrayList<BillingInfo>();
    @Transient
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<Address> addresses = new ArrayList<Address>();
    @Transient
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<Alert> alerts = new ArrayList<Alert>();
    @Transient
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<AuthProvider> authProviders = new ArrayList<AuthProvider>();
    @Transient
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<QrCode> qrCodes = new ArrayList<QrCode>();


    // ONE TO ONE
    @Transient
    @OneToOne(mappedBy = "user")
    private Subscription subscription;
    @Transient
    @OneToOne(mappedBy = "user")
    private PublicProfile publicProfile;

    // DATE TIME CONTROLS
    @Column(name = "birthday")
    private LocalDateTime birthday;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // CONSTRUCTORS

    public User(String firstName, String lastName, String password, String email, UserConstants.Title title) {
        this.publicProfile = new PublicProfile(firstName+" "+lastName,email,true);
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.createdAt = this.updatedAt = this.birthday = LocalDateTime.now();
        this.title = title;
        this.role = UserConstants.Role.USER;
    }
    public User() {
        super();
        this.publicProfile = new PublicProfile();
        this.createdAt = this.updatedAt = this.birthday = LocalDateTime.now();
        this.title = UserConstants.Title.Mr;
        this.role = UserConstants.Role.USER;
    }

}
