package com.neptunebank.account_service.dto.branchDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.account_service.dto.branchDTO
 * Created by: Ashish Kushwaha on 28-07-2025 22:56
 * File: BranchesDTO
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
public class BranchesDTO {
    @NotBlank(message = "Branch name cannot be blank")
    private String branchName;

    @NotBlank(message = "Branch code cannot be blank")
    private String branchCode;

    @NotBlank(message = "Branch address cannot be blank")
    private String branchAddress;
}
