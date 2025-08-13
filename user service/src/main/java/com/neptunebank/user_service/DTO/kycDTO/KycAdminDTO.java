package com.neptunebank.user_service.DTO.kycDTO;

import com.neptunebank.user_service.ENUMs.Status;
import lombok.*;

import java.time.LocalDateTime;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.user_service.DTO.kycDTO
 * Created by: Ashish Kushwaha on 27-07-2025 01:27
 * File: KycAdminDTO
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KycAdminDTO {

    private Long kycId;

    private Long user;

    private String aadhaarNumber;

    private byte[] aadhaarImage;

    private Boolean aadhaarVerified;

    private String panNumber;

    private byte[] panImage;

    private Boolean panVerified;

    private byte[] userPhoto;

    private Boolean userPhotoVerified;

    private byte[] userSignature;

    private Boolean userSignatureVerified;

    private String voterId;

    private byte[] voterIdImage;

    private Boolean voterIdVerified;

    private String passportNumber;

    private byte[] passportImage;

    private Boolean passportVerified;

    private String drivingLicenseNumber;

    private byte[] drivingLicenseImage;

    private Boolean drivingLicenseVerified;

    private Long verifiedByEmployeeId;

    private Status status;

    private String rejectionReason;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
