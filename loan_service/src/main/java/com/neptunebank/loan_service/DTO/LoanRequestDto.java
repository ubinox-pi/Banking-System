package com.neptunebank.loan_service.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.loan_service.DTO
 * Created by: Ashish Kushwaha on 14-07-2025 14:37
 * File: LoanRequestDto
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
public class LoanRequestDto {
    @NotNull(message = "Account ID cannot be blank")
    private Long accountId;

    @NotBlank(message = "Loan type cannot be blank")
    private String loanType;

    @NotBlank(message = "Principal amount cannot be blank")
    private String principalAmount;

    @NotBlank(message = "Tenure cannot be blank")
    private String tenure;
}
