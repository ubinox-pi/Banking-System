package com.neptunebank.account_service.service;

import com.neptunebank.account_service.dto.accountDTO.AccountAdminDTO;
import com.neptunebank.account_service.dto.accountDTO.AccountRequestDto;
import com.neptunebank.account_service.mappers.AccountMapper;
import com.neptunebank.account_service.models.Account;
import com.neptunebank.account_service.repositories.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.account_service.service
 * Created by: Ashish Kushwaha on 24-06-2025 19:56
 * File: AccountService
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
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public ResponseEntity<?> createAccount(AccountRequestDto accountRequestDto) {
        var account = AccountMapper.toEntity(accountRequestDto);
        account.setAccountNumber(generateAccountNumber());
        accountRepository.save(account);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Account created successfully");
        response.put("status", "success");
        response.put("code", "201");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<?> getAccountsByUserId(Long userId) {
        Map<String, Object> response = new HashMap<>();
        var accounts = accountRepository.findAccountByUserId(userId);
        List<AccountAdminDTO> accountAdminDTOS = new ArrayList<>();
        if (accounts != null && !accounts.isEmpty())
            for (Account account : accounts) {
                accountAdminDTOS.add(AccountMapper.toDto(account));
            }
        else {
            response.put("message", "No accounts found for the user");
            response.put("status", "error");
            response.put("code", "404");
            response.put("accounts", null);
            response.put("error", "No accounts found for the provided user ID");
            response.put("userId", String.valueOf(userId));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.put("message", "Accounts retrieved successfully");
        response.put("status", "success");
        response.put("code", "200");
        response.put("userId", String.valueOf(userId));
        response.put("accounts", accountAdminDTOS);
        response.put("error", null);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    private String generateAccountNumber() {
        String accountNumber;
        do {
            accountNumber = String.valueOf((long) (Math.random() * 9000000000L) + 1000000000L);
        } while (accountRepository.existsByAccountNumber(accountNumber));
        return accountNumber;
    }
}
