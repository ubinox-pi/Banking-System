/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See LICENSE file for details.
 *
 *
 */

package com.neptuneBank.models;

import jakarta.persistence.*;
import lombok.*;

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
    @JoinColumn(name = "user_id", referencedColumnName = "userid")
    private Users userId;

    @Column(nullable = false, unique = true)
    private String aadharNumber;

    @Column(nullable = false, columnDefinition = "BYTEA")
    private Byte aadharImage;

    @Builder.Default
    private Boolean aadharVerified = false;

    @Column(nullable = false, unique = true)
    private String panNumber;

    @Column(nullable = false, columnDefinition = "BYTEA")
    private Byte panImage;

    @Builder.Default
    private Boolean panVerified = false;

    @Column(unique = true)
    private String voterId;

    @Column(columnDefinition = "BYTEA")
    private Byte voterIdImage;

    @Builder.Default
    private Boolean voterIdVerified = false;

    @Column(unique = true)
    private String passportNumber;

    @Column(columnDefinition = "BYTEA")
    private Byte passportImage;

    @Builder.Default
    private Boolean passportVerified = false;

    @Column(unique = true)
    private String drivingLicenseNumber;

    @Column(columnDefinition = "BYTEA")
    private Byte drivingLicenseImage;

    @Builder.Default
    private Boolean drivingLicenseVerified = false;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "verifiedBy", referencedColumnName = "employeeId")
    private Employee verifiedBy;

}
