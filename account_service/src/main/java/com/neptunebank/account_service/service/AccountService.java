package com.neptunebank.account_service.service;

import com.neptunebank.account_service.ENUM.AccountType;
import com.neptunebank.account_service.ENUM.ModeOfOperation;
import com.neptunebank.account_service.ENUM.Status;
import com.neptunebank.account_service.dto.accountDTO.AccountAdminDTO;
import com.neptunebank.account_service.dto.accountDTO.AccountRequestDto;
import com.neptunebank.account_service.mappers.AccountMapper;
import com.neptunebank.account_service.models.Account;
import com.neptunebank.account_service.models.Branch;
import com.neptunebank.account_service.repositories.AccountRepository;
import com.neptunebank.account_service.repositories.BranchRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    private final AccountRepository accountRepository;
    private final KafkaTemplate<String, String> message;

    private final BranchRepository branchRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, KafkaTemplate<String, String> message, BranchRepository branchRepository) {
        this.accountRepository = accountRepository;
        this.message = message;
        this.branchRepository = branchRepository;
    }

    @Transactional
    public ResponseEntity<?> createAccount(AccountRequestDto accountRequestDto) {
        Branch branch = null;
        if (accountRepository.existsAccountByAccountType(accountRequestDto.getUserId(), accountRequestDto.getAccountType())) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Account with type " + accountRequestDto.getAccountType().toString() + " already exists for the user");
            response.put("status", "error");
            response.put("code", "409");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        if (accountRepository.existsAccountByAccountTypeAndStatus(accountRequestDto.getUserId(), accountRequestDto.getAccountType(), Status.PENDING_VERIFICATION)) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Account with type " + accountRequestDto.getAccountType().toString() + " is pending verification");
            response.put("status", "error");
            response.put("code", "409");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        if (branchRepository.existsByBranchCode(accountRequestDto.getBranchCode())) {
            branch = branchRepository.findByBranchCode(accountRequestDto.getBranchCode());
            if (branch == null) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Branch with code " + accountRequestDto.getBranchCode() + " does not exist");
                response.put("status", "error");
                response.put("code", "404");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }

        var account = AccountMapper.toEntity(accountRequestDto);
        account.setAccountNumber(generateAccountNumber());
        account.setBranch(branch);
        accountRepository.save(account);

        if (!account.getStatus().toString().isBlank() && account.getStatus().equals(Status.ACTIVE))
            message.send("set-account", account.getUserId() + ":" + account.getAccountId());

        Map<String, String> response = new HashMap<>();
        response.put("message", "Account created successfully. It will active soon as verified by the bank");
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

    @Transactional
    @KafkaListener(topics = "create-account", groupId = "users")
    protected void openAccount(String message, Acknowledgment ack) {
        String[] parts = message.split(":");
        Long userId = Long.parseLong(parts[0]);
        String accountType = parts[1];
        String status = parts[2];
        String user = parts[4];
        String email = parts[5];
        String modeOfOperation = parts[3];
        AccountRequestDto accountRequestDto = new AccountRequestDto();
        accountRequestDto.setUserId(userId);
        accountRequestDto.setAccountType(AccountType.valueOf(accountType));
        accountRequestDto.setStatus(Status.valueOf(status));
        accountRequestDto.setModeOfOperation(ModeOfOperation.valueOf(modeOfOperation));
        accountRequestDto.setBranchCode("NEPT000001");
        accountRequestDto.setAccountInterestRate(setInterestRate(AccountType.valueOf(accountType)));
        try {
            createAccount(accountRequestDto);
            String messages = accountRequestDto.getUserId() + ":" + user + ":" + email;
            this.message.send("create-banking", messages);
            ack.acknowledge();
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
    }

    @KafkaListener(topics = "check-account", groupId = "users")
    private void checkAccountNumberIsValid(String message, Acknowledgment acknowledgment) {
        boolean isValid = accountRepository.existsByAccountNumber(message);
        try {
            String userId = accountRepository.findByAccountNumber(message).orElseThrow(
                    () -> new Exception("Account not found")
            ).getUserId().toString();
            String finalMessage = message + ":" + userId + ":" + isValid;
            this.message.send("check-account-validation", finalMessage);
        } catch (Throwable e) {
            String finalMessage = message + ":" + Boolean.FALSE;
            this.message.send("check-account-validation", finalMessage);
        } finally {
            acknowledgment.acknowledge();
        }
    }

    private BigDecimal setInterestRate(AccountType type) {
        return switch (type) {
            case SAVINGS -> BigDecimal.valueOf(04.25);
            case CURRENT -> BigDecimal.valueOf(00.00);
            default -> BigDecimal.valueOf(0);
        };
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    protected void giveMonthlyInterest() {
        accountRepository.giveInterest(AccountType.SAVINGS, Status.ACTIVE); //TODO: to enhance in future
        String subject = "Monthly Interest Credited to Your Account";
        String body = """
                Dear [%s],
                
                We are pleased to inform you that the monthly interest has been credited to your account ending with [last four digits].
                
                You can now view the updated balance in your account. If you notice any discrepancies, please contact our support team immediately at [support email/phone number].
                
                Thank you for banking with [Your Company/Bank Name]. We remain committed to serving you with the highest level of trust and excellence.
                
                Sincerely,
                [Ashish kushwaha]
                [Creator]
                [Neptune Bank]
                [konealeabo@gmail.com]
                """;
        //String finalMessage = "email" + accountRepository.getUserIdByaccountNukmber("");
        //this.message.send("send-user-message", finalMessage);
    }
}
