package com.neptunebank.bankingservice.services;

import com.neptunebank.bankingservice.ENUMs.RecoveryPhrases;
import com.neptunebank.bankingservice.models.Banking;
import com.neptunebank.bankingservice.repositories.BankingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.bankingservice.services
 * Created by: Ashish Kushwaha on 20-08-2025 16:26
 * File: BankingServices
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
public class BankingServices {

    private BankingRepository bankingRepository;
    private KafkaTemplate<String, String> sendMessage;

    @Autowired
    public void setSendMessage(KafkaTemplate<String, String> message) {
        this.sendMessage = message;
    }

    @Autowired
    public void setBankingRepository(BankingRepository bankingRepository) {
        this.bankingRepository = bankingRepository;
    }

    public ResponseEntity<?> createBankingAccount(String username, String password, String newUsername, String newPassword, RecoveryPhrases recoveryPhrase, String recoveryAnswer) {
        Map<String, String> response = new HashMap<>();
        if (!username.startsWith("NEPT")) {
            response.put("error", "Invalid details");
            response.put("message", "Something is wrong with the username. Contact the admin");
            response.put("code", "400");
            response.put("status", "Failed");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if (newUsername.contains("\\d+")) {
            response.put("error", "Invalid details");
            response.put("message", "Username cannot contain numbers");
            response.put("code", "400");
            response.put("status", "Failed");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if (bankingRepository.findUsername(username, password)) {
            var account = bankingRepository.findByUsername(username);
            if (account == null) {
                response.put("error", "Invalid details");
                response.put("message", "Something went wrong");
                response.put("status", "Failed");
                response.put("code", "500");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            account.setUsername(newUsername);
            account.setPassword(newPassword);
            account.setRecoveryPhrase(recoveryPhrase);
            account.setRecoveryAnswer(recoveryAnswer);
            try {
                bankingRepository.save(account);
                response.put("message", "Account created successfully");
                response.put("status", "success");
                response.put("code", "200");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception e) {
                response.put("error", "Something went wrong");
                response.put("message", "Please try again later");
                response.put("status", "Failed");
                response.put("code", "500");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            response.put("error", "Invalid details");
            response.put("message", "Invalid username or password");
            response.put("code", "400");
            response.put("status", "Failed");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @KafkaListener(topics = "create-banking", groupId = "users")
    private void createNewBanking(String message, Acknowledgment ack) {
        String userId = message.split(":")[0];
        String customerName = message.split(":")[1];
        String email = message.split(":")[2];
        if (bankingRepository.existsByUserId(Long.parseLong(userId))) {
            ack.acknowledge();
            return;
        }
        var banking = Banking.builder()
                .userId(Long.parseLong(message))
                .username(generateUsername())
                .password(generatePassword())
                .build();
        try {
            bankingRepository.save(banking);
            String inform = "Dear " + customerName + ",\n\n" +
                    "We are pleased to inform you that your KYC verification has been successfully completed " +
                    "and your new Neptune Bank account is now active.\n\n" +
                    "To help you get started, we have generated a temporary Login ID and Password for your first login:\n\n" +
                    "Login ID: " + banking.getUsername() + "\n" +
                    "Temporary Password: " + banking.getPassword() + "\n\n" +
                    "For security reasons, please log in to your account at your earliest convenience " +
                    "and change your password immediately.\n\n" +
                    "Security Tips:\n" +
                    "- Do not share your login credentials with anyone.\n" +
                    "- Always ensure you access your account through the official Neptune Bank portal or mobile app.\n" +
                    "- If you suspect any unauthorized activity, contact our support team immediately.\n\n" +
                    "We are delighted to have you as a valued customer and look forward to serving your banking needs.\n\n" +
                    "Warm regards,\n" +
                    "Ashish kushwaha\n" +
                    "Neptune Bank\n" +
                    "Website: www.neptunebank.online";
            String subject = "Your Neptune Bank Account is Active";
            sendMessage.send("send-mail-message", email + ":" + subject + ":" + inform);

        } catch (Exception e) {
            // TODO: HANDLE EXCEPTION PROPERLY
        } finally {
            ack.acknowledge();
        }
    }

    private String generateUsername() {
        String username;
        do {
            username = "NEPT" + ((char) (Math.random() * 9000000000L) + 1000000000L);
        } while (bankingRepository.findUsername(username));
        return username;
    }

    private String generatePassword() {
        return String.valueOf((long) (Math.random() * 9000000000L) + 1000000000L);
    }
}
