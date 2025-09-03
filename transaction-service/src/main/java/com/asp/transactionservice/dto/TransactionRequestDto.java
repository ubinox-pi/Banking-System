package com.asp.transactionservice.dto;

import com.asp.transactionservice.enumeration.ModeOfTransaction;
import com.asp.transactionservice.enumeration.TransactionMedium;
import com.asp.transactionservice.enumeration.TransactionType;
import lombok.*;

import java.math.BigInteger;

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
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRequestDto {
    private Long sourceAccount;
    private Long destinationAccount;
    private TransactionType transactionType;
    private ModeOfTransaction modeOfTransaction;
    private TransactionMedium transactionMedium;
    private BigInteger amount;
    private SourceOrDestinationRequestDto sourceOrDestinationBank;
    private String description;
    private String remarks;
}
