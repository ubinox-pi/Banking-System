package com.asp.transactionservice.mapper;

import com.asp.transactionservice.dto.SourceOrDestinationRequestDto;
import com.asp.transactionservice.models.SourceOrDestinationBank;

/*
 * Copyright (c) 2025 Ayshi Shannidhya Panda. All rights reserved.
 *
 * This source code is confidential and intended solely for internal use.
 * Unauthorized copying, modification, distribution, or disclosure of this
 * file, via any medium, is strictly prohibited.
 *
 * Project: Neptune Bank
 * Author: Ayshi Shannidhya Panda
 * Created on: 02-09-2025
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
