package com.neptunebank.transaction_service.mapper;

import com.neptunebank.transaction_service.DTOs.TransactionRequestDto;
import com.neptunebank.transaction_service.models.Transaction;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.transaction_service.mapper
 * Created by: Ashish Kushwaha on 28-06-2025 13:21
 * File: TransactionMapper
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
public class TransactionMapper {
    public static Transaction toEntity(TransactionRequestDto dto) {
        return Transaction.builder()
                .sourceAccount(dto.getSourceAccount())
                .destinationAccount(dto.getDestinationAccount())
                .transactionType(dto.getTransactionType())
                .modeOfTransaction(dto.getModeOfTransaction())
                .transactionMedium(dto.getTransactionMedium())
                .amount(dto.getAmount())
                .sourceOrDestinationBankId(SourceOrDestinationMapper.toEntity(dto.getSourceOrDestinationBank()))
                .description(dto.getDescription())
                .remarks(dto.getRemarks())
                .build();
    }
}
