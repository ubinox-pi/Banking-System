package com.neptunebank.user_service.DTO.kycDTO;

import com.neptunebank.user_service.ENUMs.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.user_service.DTO.kycDTO
 * Created by: Ashish Kushwaha on 17-08-2025 01:07
 * File: KycVerificationDTO
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
public class KycVerificationDTO {

    @NotNull(message = "userId cannot be null")
    private Long userId;
    
    @NotNull(message = "aadhaar verification cannot be null")
    private Boolean aadhaarVerified;

    @NotNull(message = "pan verification cannot be blank")
    private Boolean panVerified;

    @NotNull(message = "user photo verification cannot be null")
    private Boolean userPhotoVerified;

    @NotNull(message = "user signature verification cannot be null")
    private Boolean userSignatureVerified;

    @NotNull(message = "voterId verification cannot be null")
    private Boolean voterIdVerified;

    @NotNull(message = "passport verification cannot be null")
    private Boolean passportVerified;

    @NotNull(message = "drivingLicense verification cannot be null")
    private Boolean drivingLicenseVerified;

    @NotNull(message = "verifiedByEmployeeId cannot be null")
    private Long verifiedByEmployeeId;

    @NotNull(message = "status cannot be blank")
    private Status status;

    @NotBlank(message = "rejectionReason cannot be blank")
    private String rejectionReason;


}
