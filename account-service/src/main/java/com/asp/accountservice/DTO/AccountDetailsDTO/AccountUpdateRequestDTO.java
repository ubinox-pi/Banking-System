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
 * Created on: 11-09-2025
 */

import com.asp.accountservice.enumeration.AccountType;
import com.asp.accountservice.enumeration.ModeOfOperation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountUpdateRequestDTO {

    @NotNull(message = "Account type is required")
    private AccountType accountType;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Branch code is required")
    private String branchCode;

    @NotNull(message = "Mode of Operation is required")
    private ModeOfOperation modeOfOperation;

    @NotNull(message = "Balance is required")
    @PositiveOrZero
    private BigDecimal balance;
}
