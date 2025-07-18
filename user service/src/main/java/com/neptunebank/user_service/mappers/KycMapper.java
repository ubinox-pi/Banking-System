package com.neptunebank.user_service.mappers;

import com.neptunebank.user_service.DTO.kycDTO.KycRequestDTO;
import com.neptunebank.user_service.models.Kyc;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.user_service.mappers
 * Created by: Ashish Kushwaha on 25-05-2025 14:59
 * File: KycMapper
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
public class KycMapper {

    public static Kyc toEntity(KycRequestDTO dto) {
        return Kyc.builder()
                .aadhaarNumber(dto.getAadhaarNumber())
                .panNumber(dto.getPanNumber())
                .voterId(dto.getVoterId())
                .passportNumber(dto.getPassportNumber())
                .drivingLicenseNumber(dto.getDrivingLicenseNumber())
                .build();
    }
}
