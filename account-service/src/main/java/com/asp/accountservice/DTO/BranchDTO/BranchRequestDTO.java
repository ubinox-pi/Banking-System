package com.asp.accountservice.DTO.BranchDTO;

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

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchRequestDTO {
    @NotBlank(message = "Branch code is required")
    private String branchCode;

    @NotBlank(message = "Branch Name is required")
    private String branchName;

    @NotBlank(message = "Branch Address is required")
    private String branchAddress;

    @NotBlank(message = "Branch city is required")
    private String branchCity;

    @NotBlank(message = "Branch state is required")
    private String branchState;

    @NotBlank(message = "Branch zip is required")
    private String branchZip;
}
