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

package com.neptunebank.employeeservice.models;

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
    private Employee userId;

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

    @Column(nullable = false, unique = true)
    private Long verifiedByEmployeeId;

}
