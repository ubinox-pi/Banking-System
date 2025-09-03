package com.asp.transactionservice.mapper;

import com.asp.transactionservice.dto.TransactionRequestDto;
import com.asp.transactionservice.models.Transaction;

/*
 * Copyright (c) 2025 Ayshi Shannidhya Panda. All rights reserved.
 *
 * This source code is confidential and intended solely for internal use.
 * Unauthorized copying, modification, distribution, or disclosure of this
 * file, via any medium, is strictly prohibited.
 *
 * Project: Neptune Bank
 * Author: Ayshi Shannidhya Panda
 * Created on: 02-09-2025
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
