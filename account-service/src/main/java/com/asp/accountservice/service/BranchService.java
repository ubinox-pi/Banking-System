package com.asp.accountservice.service;

import com.asp.accountservice.DTO.BranchDTO.BranchRequestDTO;
import com.asp.accountservice.DTO.BranchDTO.BranchResponseDTO;
import com.asp.accountservice.models.Branch;
import com.asp.accountservice.repositories.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * Copyright (c) 2025 Ayshi Shannidhya Panda. All rights reserved.
 *
 * This source code is confidential and intended solely for internal use.
 * Unauthorized copying, modification, distribution, or disclosure of this
 * file, via any medium, is strictly prohibited.
 *
 * Project: Neptune Bank
 * Author: Ayshi Shannidhya Panda
 * Created on: 10-09-2025
 */
@Service
public class BranchService {
    private final BranchRepository branchRepository;

    @Autowired
    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public BranchResponseDTO createBranch(BranchRequestDTO requestDTO) {
        if (branchRepository.existsByBranchCode(requestDTO.getBranchCode())) {
            return BranchResponseDTO.builder()
                    .success(false)
                    .message("Branch with code '" + requestDTO.getBranchCode() + "' already exists")
                    .build();
        }
        Branch branch = Branch.builder()
                .branchCode(requestDTO.getBranchCode())
                .branchName(requestDTO.getBranchName())
                .branchAddress(requestDTO.getBranchAddress())
                .branchCity(requestDTO.getBranchCity())
                .branchState(requestDTO.getBranchState())
                .branchZip(requestDTO.getBranchZip())
                .build();

        Branch saved = branchRepository.save(branch);

        return BranchResponseDTO.builder()
                .branchId(saved.getBranchId())
                .branchCode(saved.getBranchCode())
                .branchName(saved.getBranchName())
                .branchAddress(saved.getBranchAddress())
                .branchCity(saved.getBranchCity())
                .branchState(saved.getBranchState())
                .branchZip(saved.getBranchZip())
                .success(true)
                .message("Branch created successfully")
                .build();
    }
}
