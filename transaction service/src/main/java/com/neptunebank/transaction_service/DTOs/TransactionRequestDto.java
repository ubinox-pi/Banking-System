package com.neptunebank.transaction_service.DTOs;

import com.neptunebank.transaction_service.ENUM.ModeOfTransaction;
import com.neptunebank.transaction_service.ENUM.TransactionMedium;
import com.neptunebank.transaction_service.ENUM.TransactionType;
import lombok.*;

import java.math.BigInteger;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.transaction_service.DTOs
 * Created by: Ashish Kushwaha on 25-06-2025 20:44
 * File: TransactionDto
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRequestDto {
    private SourceOrDestinationRequestDto sourceAccount;
    private SourceOrDestinationRequestDto destinationAccount;
    private TransactionType transactionType;
    private ModeOfTransaction modeOfTransaction;
    private TransactionMedium transactionMedium;
    private BigInteger amount;
    private String description;
    private String remarks;
}
