package com.asp.accountservice.DTO.AccountDetailsDTO;

/*
 * Copyright (c) 2025 Ayshi Shannidhya Panda. All rights reserved.
 *
 * This source code is confidential and intended solely for internal use.
 * Unauthorized copying, modification, distribution, or disclosure of this
 * file, via any medium, is strictly prohibited.
 *
 * Project: Neptune Bank
 * Author: Ayshi Shannidhya Panda
 * Created on: 29-06-2025
 */

import com.asp.accountservice.DTO.BranchDTO.BranchResponseDTO;
import com.asp.accountservice.enumeration.AccountType;
import com.asp.accountservice.enumeration.ModeOfOperation;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountResponseDTO {


    private Long accountId;

    private String accountNumber;

    @Enumerated
    private AccountType accountType;

    private BigDecimal balance;

    private Long userId;

    @Enumerated
    private ModeOfOperation modeOfOperation;

    private Long branchId;

    private String branchCode;

    private BranchResponseDTO branch;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private boolean success;

    private String message;
}
