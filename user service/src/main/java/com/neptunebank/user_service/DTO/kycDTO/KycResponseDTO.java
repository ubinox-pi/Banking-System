package com.neptunebank.user_service.DTO.kycDTO;

import com.neptunebank.user_service.models.Users;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.user_service.DTO.kycDTO
 * Created by: Ashish Kushwaha on 25-05-2025 14:45
 * File: KycResponseDTO
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
public class KycResponseDTO {
    private Users user;
    private String aadharNumber;
    private Byte aadharImage;
    private Boolean aadharVerified;
    private String panNumber;
    private Byte panImage;
    private Boolean panVerified;
    private String voterId;
    private Byte voterIdImage;
    private Boolean voterIdVerified;
    private String passportNumber;
    private Byte passportImage;
    private Boolean passportVerified;
    private String drivingLicenseNumber;
    private Byte drivingLicenseImage;
    private Boolean drivingLicenseVerified;
    private Long verifiedByEmployeeId;
    private String rejectionReason;
}
