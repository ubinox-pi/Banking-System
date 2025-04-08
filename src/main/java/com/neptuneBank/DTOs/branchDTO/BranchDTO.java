/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See LICENSE file for details.
 *
 *
 */

package com.neptuneBank.DTOs.branchDTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BranchDTO {
    private String branchId;
    private String swiftId;
    private String branchName;
    private String branchAddress;
    private String branchCity;
    private String branchState;
    private String branchZip;
    private String branchCountry;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
