package com.neptunebank.transaction_service.mapper;

import com.neptunebank.transaction_service.DTOs.SourceOrDestinationRequestDto;
import com.neptunebank.transaction_service.models.SourceOrDestinationBank;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.transaction_service.mapper
 * Created by: Ashish Kushwaha on 28-06-2025 13:36
 * File: SourceOrDestinationMapper
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
public class SourceOrDestinationMapper {
    public static SourceOrDestinationBank toEntity(SourceOrDestinationRequestDto dto) {
        return SourceOrDestinationBank.builder()
                .bankName(dto.getBankName())
                .bankIFSCCode(dto.getBankIFSCCode())
                .bankBranchName(dto.getBankBranchName())
                .bankBranchCity(dto.getBankBranchCity())
                .bankBranchState(dto.getBankBranchState())
                .bankBranchCountry(dto.getBankBranchCountry())
                .bankBranchZipCode(dto.getBankBranchZipCode())
                .build();
    }
}
