package com.neptunebank.transaction_service.services;

import com.neptunebank.transaction_service.DTOs.TransactionRequestDto;
import com.neptunebank.transaction_service.mapper.TransactionMapper;
import com.neptunebank.transaction_service.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.transaction_service.services
 * Created by: Ashish Kushwaha on 28-06-2025 13:17
 * File: TransactionService
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
@Service
public class TransactionService {
    private TransactionRepository transactionRepository;

    @Autowired
    public void setTransactionRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public ResponseEntity<?> createTransaction(TransactionRequestDto transactionRequestDto) {
        var transaction = TransactionMapper.toEntity(transactionRequestDto);
        transaction.setTransactionId(getTransactionId());
        String transactionId = transactionRepository.save(transaction).getTransactionId();
        Map<String, String> response = new HashMap<>();
        response.put("message", "Transaction created successfully");
        response.put("transactionId", transactionId);
        response.put("status", "success");
        response.put("code", "201");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    private String getTransactionId() {
        String transactionId;
        do {
            transactionId = generateTransactionId();
        }
        while (transactionRepository.existsByTransactionId(transactionId));
        return transactionId;
    }

    private String generateTransactionId() {
        StringBuilder sb = new StringBuilder("NEFT");
        for (int i = 0; i < 60; i++) {
            sb.append((int) (Math.random() * 10));
        }
        return sb.toString();
    }

}
