package com.neptunebank.account_service.mappers;

import com.neptunebank.account_service.dto.branchDTO.BranchAdminDTO;
import com.neptunebank.account_service.dto.branchDTO.BranchesDTO;
import com.neptunebank.account_service.models.Branches;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.account_service.mappers
 * Created by: Ashish Kushwaha on 28-07-2025 22:59
 * File: BranchMapper
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
public class BranchMapper {
    public static Branches toEntity(BranchesDTO dto) {
        return Branches.builder()
                .branchName(dto.getBranchName())
                .branchCode(dto.getBranchCode())
                .branchAddress(dto.getBranchAddress())
                .build();
    }

    public static BranchAdminDTO toDto(Branches branch) {
        return BranchAdminDTO.builder()
                .branchId(branch.getBranchId())
                .branchName(branch.getBranchName())
                .branchCode(branch.getBranchCode())
                .branchAddress(branch.getBranchAddress())
                .createdAt(branch.getCreatedAt())
                .updatedAt(branch.getUpdatedAt())
                .build();
    }
}
