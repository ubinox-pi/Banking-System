package com.neptunebank.card_service.service;

import com.neptunebank.card_service.DTO.CardDTO;
import com.neptunebank.card_service.ENUM.CardStatus;
import com.neptunebank.card_service.model.Card;
import com.neptunebank.card_service.repositories.CardRepository;
import com.neptunebank.card_service.repositories.CardTypeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.card_service.service
 * Created by: Ashish Kushwaha on 03-09-2025 20:29
 * File: CardService
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
public class CardService {

    private final CardRepository cardRepository;
    private final KafkaTemplate<String, String> message;
    private final Map<String, Boolean> accountValidationCache;
    private final Map<Long, Boolean> employeeValidationCache;
    private final Map<String, String> userValidationCache;
    private final CardTypeRepository cardTypeRepository;

    @Autowired
    public CardService(CardRepository cardRepository, KafkaTemplate<String, String> kafkaTemplate, CardTypeRepository cardTypeRepository) {
        this.cardRepository = cardRepository;
        this.message = kafkaTemplate;
        this.cardTypeRepository = cardTypeRepository;
        this.accountValidationCache = new ConcurrentHashMap<>();
        this.employeeValidationCache = new ConcurrentHashMap<>();
        this.userValidationCache = new ConcurrentHashMap<>();
    }

    @Transactional
    public ResponseEntity<?> createCard(CardDTO dto) {
        Map<String, String> response = new HashMap<>();
        accountValidationCache.put(dto.getAccountNumber(), null);
        if (!isAccountNumberValid(dto.getAccountNumber())) {
            response.put("message", "Account number is invalid.");
            response.put("status", "failed");
            response.put("code", "400");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if (cardRepository.isCardAlreadyExistsByAccountNumber(dto.getAccountNumber(), CardStatus.PENDING)) {
            response.put("message", "Previous card creation request is still pending.");
            response.put("status", "failed");
            response.put("code", "400");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        var card = Card.builder()
                .accountNumber(dto.getAccountNumber())
                .cardHolderName(dto.getCardHolderName())
                .cardNetworkType(dto.getCardNetworkType())
                .contactlessEnabled(dto.getContactlessEnabled())
                .status(CardStatus.PENDING)
                .cardNumber(generateCardNumber())
                .expiryDate(LocalDate.of(2026, 10, 3))
                .cvv(String.valueOf(ThreadLocalRandom.current().nextInt(111, 999)))
                .dailyLimit(BigDecimal.valueOf(50000))
                .monthlyLimit(BigDecimal.valueOf(5000000))
                .issuedAt(LocalDateTime.now())
                .build();

        if (cardTypeRepository.existsCardTypesByCardTypeId(dto.getCardType())) {
            var cardType = cardTypeRepository.findCardTypesByCardTypeId(dto.getCardType());
            card.setCardType(cardType);
        } else {
            response.put("message", "Card type is invalid.");
            response.put("status", "failed");
            response.put("code", "400");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        cardRepository.save(card);

        response.put("message", "Card created successfully and is pending activation.");
        response.put("status", "success");
        response.put("code", "201");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    private String generateCardNumber() {
        long min = 1_000_000_000_000_000L;
        long max = 10_000_000_000_000_000L;
        String value;
        do {
            value = String.valueOf(ThreadLocalRandom.current().nextLong(min, max));
        } while (cardRepository.existsByCardNumber(value));
        return value;
    }

    private boolean isAccountNumberValid(String accountNumber) {
        message.send("check-account", accountNumber);
        int count = 0;
        while (true) {
            if (accountValidationCache.get(accountNumber) != null) {
                return accountValidationCache.get(accountNumber);
            } else {
                if (count > 10) {
                    return false;
                }
                count++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private boolean isEmployeeIdValid(String employeeId) {
        message.send("check-employee-for-card", employeeId);
        int count = 0;
        while (true) {
            if (employeeValidationCache.get(Long.parseLong(employeeId)) != null) {
                return employeeValidationCache.get(Long.parseLong(employeeId));
            } else {
                if (count > 10) {
                    return false;
                }
                count++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private boolean isPhoneFound(String userId) {
        this.message.send("get-user-phone", userId);
        int count = 0;
        while (true) {
            if (userValidationCache.get(userId) != null) {
                return employeeValidationCache.get(Long.parseLong(userId));
            } else {
                if (count > 10) {
                    return false;
                }
                count++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    @Transactional
    public ResponseEntity<?> verifyCard(String cardNumber, Long employeeId, CardStatus status) {
        Map<String, String> response = new HashMap<>();
        if (cardRepository.existsByCardNumber(cardNumber)) {
            employeeValidationCache.put(employeeId, null);
            if (isEmployeeIdValid(String.valueOf(employeeId))) {
                if (status.equals(CardStatus.ACTIVE)) {
                    var card = cardRepository.findByCardNumber(cardNumber);
                    card.setVarifiedBY(employeeId);
                    card.setStatus(status);
                    cardRepository.save(card);
                    response.put("message", "Card verified successfully.");
                    response.put("status", "success");
                    response.put("code", "200");
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    response.put("message", "Card status is invalid.");
                    response.put("status", "failed");
                    response.put("code", "400");
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }
            } else {
                response.put("message", "Employee ID is invalid.");
                response.put("status", "failed");
                response.put("code", "400");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } else {
            response.put("message", "Card number is invalid.");
            response.put("status", "failed");
            response.put("code", "400");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @KafkaListener(topics = "check-account-validation", groupId = "users")
    private void setAccountValidationCache(String message, Acknowledgment acknowledgment) {
        String accountNumber = message.split(":")[0];
        String userId = message.split(":")[1];
        userValidationCache.put(userId, null);
        Boolean isValid = Boolean.parseBoolean(message.split(":")[2]);
        if (isPhoneFound(userId)) {
            String text = """
                    Dear [%s],
                    
                    We are pleased to inform you that your card ending with [last four digits] has been successfully verified.
                    
                    You may now use this card for secure and seamless transactions with us. If you did not initiate this verification, we strongly advise you to contact our support team immediately.
                    
                    Should you have any questions or require further assistance, please reach out to us at [support email/phone number].
                    
                    Thank you for your trust in [Your Company/Bank Name]. We remain committed to serving you with the highest level of security and excellence.
                    
                    Sincerely,
                    [Ashish kushwaha]
                    [Creator]
                    [Neptune Bank]
                    [konealeabo@gamil.com]
                    """;
            String finalMessage = "phone" + userId + ":" + text;
            this.message.send("send-user-message", finalMessage);
        }
        accountValidationCache.put(accountNumber, isValid);
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = "check-employee-validation", groupId = "users")
    private void setEmployeeValidationCache(String message, Acknowledgment acknowledgment) {
        String employeeId = message.split(":")[0];
        Boolean isValid = Boolean.parseBoolean(message.split(":")[1]);
        employeeValidationCache.put(Long.valueOf(employeeId), isValid);
        acknowledgment.acknowledge();
    }
}
