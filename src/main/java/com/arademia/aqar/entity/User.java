package com.arademia.aqar.entity;

import com.arademia.aqar.config.ConfigsConst;
import com.arademia.aqar.entity.billing.BillingInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Table(name = ConfigsConst.USERS)
@Entity
@EqualsAndHashCode(of = "id")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "credit")
    private Double credit;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    // ONE TO MANY
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Authority> authorities = new ArrayList<Authority>();
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<BillingInfo> billingInfo = new ArrayList<BillingInfo>();
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Address> addresses = new ArrayList<Address>();
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Alert> alerts = new ArrayList<Alert>();
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<AuthProvider> authProviders = new ArrayList<AuthProvider>();
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<ContactDetail> contactDetails = new ArrayList<ContactDetail>();
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<QrCode> qrCodes = new ArrayList<QrCode>();


    // ONE TO ONE
    @OneToOne(mappedBy = "user")
    private Subscription subscription;

    // DATE TIME CONTROLS
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
