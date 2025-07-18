package com.neptunebank.loan_service.mapper;

import com.neptunebank.loan_service.DTO.LoanRequestDto;
import com.neptunebank.loan_service.ENUM.LoanType;
import com.neptunebank.loan_service.model.Loan;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.loan_service.mapper
 * Created by: Ashish Kushwaha on 14-07-2025 14:41
 * File: LoanMapper
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
public class LoanMapper {
    public static Loan toEntity(LoanRequestDto dto) {
        return Loan.builder()
                .accountId(dto.getAccountId())
                .loanType(LoanType.valueOf(dto.getLoanType()))
                .principalAmount(dto.getPrincipalAmount())
                .tenure(dto.getTenure())
                .build();
    }
}
