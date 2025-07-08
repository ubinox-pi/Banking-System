package com.neptunebank.account_service.mappers;

import com.neptunebank.account_service.ENUM.AccountType;
import com.neptunebank.account_service.dto.AccountRequestDto;
import com.neptunebank.account_service.models.Account;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.account_service.mapper
 * Created by: Ashish Kushwaha on 24-06-2025 19:52
 * File: AccountMapper
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
public class AccountMapper {
    public static Account toEntity(AccountRequestDto accountRequestDto) {
        return Account.builder()
                .userId(accountRequestDto.getUserId())
                .branchId(accountRequestDto.getBranchId())
                .accountType(AccountType.valueOf(accountRequestDto.getAccountType().toUpperCase()))
                .status(accountRequestDto.getStatus())
                .modeOfOperation(accountRequestDto.getModeOfOperation())
                .accountInterestRate(accountRequestDto.getAccountInterestRate())
                .build();
    }
}
