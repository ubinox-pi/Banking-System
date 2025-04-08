package com.neptuneBank.models;

import com.neptuneBank.models.ENUM.Genders;
import com.neptuneBank.models.ENUM.MaritalStatus;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long uid;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(nullable = false, length = 100)
    private Date dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genders gender;

    @Column(nullable = false, length = 150)
    private String fatherName;

    @Column(nullable = false, length = 150)
    private String motherName;

    //it will take mobile number as string with country code
    @Column(nullable = false, length = 20)
    private String mobileNumber;

    @Column(nullable = false, length = 150)
    private String email;

    @Column(nullable = false)
    private MaritalStatus maritalStatus;

    @Column(length = 150)
    private String spouseName = null;

    @Column(length = 150)
    private String occupation = null;

    @Column(length = 150)
    private String country = null;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "accountId", unique = true)
    private Account account;
}
