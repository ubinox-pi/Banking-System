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

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponseDTO {

    private Long accountId;

    private String accountNumber;

    private String accountType;

    private BigDecimal balance;

    private Long userId;

    private Long branchId;
    private String branchCode;

    private LocalDateTime createdAt;
}
