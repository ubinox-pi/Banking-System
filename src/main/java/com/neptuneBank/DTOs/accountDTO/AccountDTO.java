/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See LICENSE file for details.
 *
 *
 */

package com.neptuneBank.DTOs.accountDTO;

import com.neptuneBank.models.Branch;
import com.neptuneBank.models.ENUM.AccountType;
import com.neptuneBank.models.ENUM.ModeOfOperation;
import com.neptuneBank.models.Users;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
@Setter
@NoArgsConstructor
public class AccountDTO {
    private Long accountId;
    private Users users;
    private String accountNumber;
    private Branch branch;
    @Builder.Default
    private BigDecimal balance = BigDecimal.ZERO;
    private AccountType accountType;
    private ModeOfOperation modeOfOperation;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
