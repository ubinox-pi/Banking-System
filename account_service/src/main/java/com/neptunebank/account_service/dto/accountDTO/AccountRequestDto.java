package com.neptunebank.account_service.dto.accountDTO;

import com.neptunebank.account_service.ENUM.AccountType;
import com.neptunebank.account_service.ENUM.ModeOfOperation;
import com.neptunebank.account_service.ENUM.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.account_service.dto
 * Created by: Ashish Kushwaha on 24-06-2025 19:46
 * File: AccountDto
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountRequestDto {
    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotBlank(message = "Branch ID cannot be null")
    private String branchCode;

    @NotNull(message = "Account type cannot be null")
    private AccountType accountType;

    private Status status;

    @NotNull(message = "Mode of operation cannot be null")
    private ModeOfOperation modeOfOperation;

    @NotNull(message = "Account interest rate cannot be null")
    private BigDecimal accountInterestRate;
}
