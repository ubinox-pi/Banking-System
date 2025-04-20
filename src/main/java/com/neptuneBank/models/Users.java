/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See LICENSE file for details.
 *
 *
 */


package com.neptuneBank.models;

import com.neptuneBank.ENUM.*;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long userid;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String middleName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genders gender;

    @Column(nullable = false)
    private String fatherName;

    @Column(nullable = false)
    private String motherName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    @Builder.Default
    private String spouseName = null;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Occupation occupation;

    @Column(nullable = false)
    private String salary;

    @Column(nullable = false)
    private String citizen;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Catagory category;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Religion religion;

    @Valid
    @NotNull(message = "Contact details is not mapped correctly.")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(nullable = false, name = "contactId", referencedColumnName = "contactId", unique = true)
    private ContactDetails contactDetails;

    @Valid
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "accountId", referencedColumnName = "accountId", unique = true)
    @Builder.Default
    private Account account = null;

    @Valid
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "nomineeId", referencedColumnName = "nomineeId", unique = true)
    @Builder.Default
    private Nominee nominee = null;

    @Column(nullable = false)
    @Builder.Default
    private Double accountInterestRate = 0.0d;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "kycId", referencedColumnName = "kycId", unique = true)
    private Kyc kycId;

    //This will a class in later
    @Builder.Default
    private Boolean isVerified = false;

    @Builder.Default
    private Boolean isActive = null;

    @Builder.Default
    private Boolean isBlocked = null;

    @Builder.Default
    private Boolean isDeleted = null;

    @Column(nullable = false, updatable = false)
    @PastOrPresent
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @FutureOrPresent
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }


}
