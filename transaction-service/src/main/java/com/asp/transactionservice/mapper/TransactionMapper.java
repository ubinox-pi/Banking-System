package com.asp.transactionservice.mapper;

/*
 * Copyright (c) 2025 Ayshi Shannidhya Panda. All rights reserved.
 *
 * This source code is confidential and intended solely for internal use.
 * Unauthorized copying, modification, distribution, or disclosure of this
 * file, via any medium, is strictly prohibited.
 *
 * Project: Neptune Bank
 * Author: Ayshi Shannidhya Panda
 * Created on: 30-06-2025
 */

import com.asp.transactionservice.dto.TransactionRequest;
import com.asp.transactionservice.dto.TransactionResponse;
import com.asp.transactionservice.model.Transaction;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface TransactionMapper {

    Transaction toEntity(TransactionRequest request);

    TransactionResponse toResponse(Transaction transaction);
}

