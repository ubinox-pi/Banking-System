package com.neptunebank.user_service.DTO.kycDTO;

import lombok.*;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.user_service.DTO.kycDTO
 * Created by: Ashish Kushwaha on 25-05-2025 15:14
 * File: KycVerificationRequestDTO
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KycVerificationRequestDTO {
    private Long kycId;
    private Boolean aadhaarVerified;
    private Boolean panVerified;
    private Boolean userPhotoVerified;
    private Boolean userSignatureVerified;
    private Boolean voterIdVerified;
    private Boolean passportVerified;
    private Boolean drivingLicenseVerified;
    private Long verifiedByEmployeeId;
    private String rejectionReason;
}
