/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project : Neptune
 * Created by: ASUS on 22-05-2025
 * File : Kyc.java
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

import com.neptunebank.user_service.ENUMs.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Kyc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long kycId;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "userId", referencedColumnName = "userId", unique = true, updatable = false)
    private Users user;

    @Column(nullable = false, unique = true)
    private String aadhaarNumber;

    @Column(nullable = false, columnDefinition = "BYTEA")
    private byte[] aadhaarImage;

    @Builder.Default
    private Boolean aadhaarVerified = false;

    @Column(nullable = false, unique = true)
    private String panNumber;

    @Column(nullable = false, columnDefinition = "BYTEA")
    private byte[] panImage;

    @Builder.Default
    private Boolean panVerified = false;

    @Column(nullable = false, columnDefinition = "BYTEA")
    private byte[] userPhoto;

    @Builder.Default
    private Boolean userPhotoVerified = false;

    @Column(nullable = false, columnDefinition = "BYTEA")
    private byte[] userSignature;

    @Builder.Default
    private Boolean userSignatureVerified = false;

    @Builder.Default
    private String voterId = null;

    @Column(columnDefinition = "BYTEA")
    private byte[] voterIdImage;

    @Builder.Default
    private Boolean voterIdVerified = false;

    @Builder.Default
    private String passportNumber = null;

    @Column(columnDefinition = "BYTEA")
    private byte[] passportImage;

    @Builder.Default
    private Boolean passportVerified = false;

    @Builder.Default
    private String drivingLicenseNumber = null;

    @Column(columnDefinition = "BYTEA")
    private byte[] drivingLicenseImage;

    @Builder.Default
    private Boolean drivingLicenseVerified = false;

    @Builder.Default
    private Long verifiedByEmployeeId = null;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(nullable = false)
    private Status status = Status.PENDING;

    @Builder.Default
    private String rejectionReason = null;

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

        if (this.panNumber != null)
            if (this.panNumber.equals(this.panNumber.toUpperCase()))
                this.panNumber = this.panNumber.toUpperCase().trim();

        if (this.voterId != null)
            if (this.voterId.equals(this.voterId.toUpperCase()))
                this.voterId = this.voterId.toUpperCase().trim();

        if (this.passportNumber != null)
            if (this.passportNumber.equals(this.passportNumber.toUpperCase()))
                this.passportNumber = this.passportNumber.toUpperCase().trim();

        if (this.drivingLicenseNumber != null)
            if (this.drivingLicenseNumber.equals(this.drivingLicenseNumber.toUpperCase()))
                this.drivingLicenseNumber = this.drivingLicenseNumber.toUpperCase().trim();

        if (this.rejectionReason != null)
            if (this.rejectionReason.equals(this.rejectionReason.toUpperCase()))
                this.rejectionReason = this.rejectionReason.toUpperCase().trim();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();

        if (this.panNumber != null)
            if (this.panNumber.equals(this.panNumber.toUpperCase()))
                this.panNumber = this.panNumber.toUpperCase().trim();

        if (this.voterId != null)
            if (this.voterId.equals(this.voterId.toUpperCase()))
                this.voterId = this.voterId.toUpperCase().trim();
        if (this.voterId != null && this.voterId.isEmpty() && this.voterId.isBlank())
            this.voterId = null;

        if (this.passportNumber != null)
            if (this.passportNumber.equals(this.passportNumber.toUpperCase()))
                this.passportNumber = this.passportNumber.toUpperCase().trim();
        if (this.passportNumber != null && this.passportNumber.isEmpty() && this.passportNumber.isBlank())
            this.passportNumber = null;

        if (this.drivingLicenseNumber != null)
            if (this.drivingLicenseNumber.equals(this.drivingLicenseNumber.toUpperCase()))
                this.drivingLicenseNumber = this.drivingLicenseNumber.toUpperCase().trim();
        if (this.drivingLicenseNumber != null && this.drivingLicenseNumber.isEmpty() && this.drivingLicenseNumber.isBlank())
            this.drivingLicenseNumber = null;

        if (this.rejectionReason != null)
            if (this.rejectionReason.equals(this.rejectionReason.toUpperCase()))
                this.rejectionReason = this.rejectionReason.toUpperCase().trim();
    }

}
