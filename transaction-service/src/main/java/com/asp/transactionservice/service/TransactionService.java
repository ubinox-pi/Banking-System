package com.asp.transactionservice.service;

import com.asp.transactionservice.dto.TransactionRequestDto;
import com.asp.transactionservice.enumeration.TransactionStatus;
import com.asp.transactionservice.mapper.TransactionMapper;
import com.asp.transactionservice.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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
@Service
public class TransactionService {

    private TransactionRepository transactionRepository;

    @Autowired
    public void setTransactionRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public ResponseEntity<?> createTransact(@Valid TransactionRequestDto dto) {
        var transaction = TransactionMapper.toEntity(dto);
        transaction.setTransactionId(getTransactionId());
        transaction.setTransactionStatus(TransactionStatus.PENDING);
        transactionRepository.save(transaction);
        transaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);
        transactionRepository.save(transaction);
        String transactionId = transaction.getTransactionId();
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
