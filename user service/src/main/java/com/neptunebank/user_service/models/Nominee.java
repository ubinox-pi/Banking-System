/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project : Neptune
 * Created by: ASUS on 22-05-2025
 * File : Nominee.java
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
package com.neptunebank.user_service.models;

import com.neptunebank.user_service.ENUMs.Relationship;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
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
public class Nominee {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nomineeId;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "userId", referencedColumnName = "userId", unique = true, updatable = false)
    private Users user;

    @Column(nullable = false)
    private String nomineeName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Relationship nomineeRelationship;

    @Column(nullable = false)
    @Past
    private LocalDate nomineeDateOfBirth;

    @Column(nullable = false, unique = true)
    private String nomineeMobileNumber;

    @Email
    @Column(nullable = false, unique = true)
    private String nomineeEmail;

    @Column(nullable = false, unique = true)
    private String nomineeAadhaar;

    @Column(nullable = false, unique = true)
    private String nomineePan;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String nomineeAddress;

    @Column(nullable = false, updatable = false)
    @PastOrPresent
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @PastOrPresent
    private LocalDateTime updatedAt;


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        if (this.nomineeName != null)
            if (!this.nomineeName.equals(this.nomineeName.toUpperCase()))
                this.nomineeName = this.nomineeName.toUpperCase().trim();

        if (this.nomineeRelationship != null)
            if (!this.nomineeEmail.equals(this.nomineeEmail.toUpperCase()))
                this.nomineeEmail = this.nomineeEmail.toUpperCase().trim();

        if (this.nomineeMobileNumber != null)
            if (!this.nomineeMobileNumber.startsWith("+91"))
                this.nomineeMobileNumber = "+91" + this.nomineeMobileNumber.trim();

        if (this.nomineePan != null)
            if (!this.nomineePan.equals(this.nomineePan.toUpperCase()))
                this.nomineePan = this.nomineePan.toUpperCase().trim();

        if (this.nomineeAddress != null)
            if (!this.nomineeAddress.equals(this.nomineeAddress.toUpperCase()))
                this.nomineeAddress = this.nomineeAddress.toUpperCase().trim();

    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
        if (this.nomineeName != null)
            if (!this.nomineeName.equals(this.nomineeName.toUpperCase()))
                this.nomineeName = this.nomineeName.toUpperCase().trim();

        if (this.nomineeRelationship != null)
            if (!this.nomineeEmail.equals(this.nomineeEmail.toUpperCase()))
                this.nomineeEmail = this.nomineeEmail.toUpperCase().trim();

        if (this.nomineeMobileNumber != null)
            if (!this.nomineeMobileNumber.startsWith("+91"))
                this.nomineeMobileNumber = "+91" + this.nomineeMobileNumber.trim();

        if (this.nomineePan != null)
            if (!this.nomineePan.equals(this.nomineePan.toUpperCase()))
                this.nomineePan = this.nomineePan.toUpperCase().trim();

        if (this.nomineeAddress != null)
            if (!this.nomineeAddress.equals(this.nomineeAddress.toUpperCase()))
                this.nomineeAddress = this.nomineeAddress.toUpperCase().trim();
    }
}
