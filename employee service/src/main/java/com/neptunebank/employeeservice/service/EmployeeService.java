package com.neptunebank.employeeservice.service;

import com.neptunebank.employeeservice.models.POJO.kycService.KycRequest;
import com.neptunebank.employeeservice.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.employeeservice.service
 * Created by: Ashish Kushwaha on 23-05-2025 19:18
 * File: EmployeeService
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
public class EmployeeService {

    private final Map<Long, CompletableFuture<String>> futureMap = new ConcurrentHashMap<>();
    private final Map<String, String> response = new ConcurrentHashMap<>();
    private EmployeeRepository employeeRepository;
    private KafkaTemplate<String, KycRequest> data;
    private KafkaTemplate<String, String> message;

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Autowired
    public void setData(KafkaTemplate<String, KycRequest> kafkaTemplate) {
        this.data = kafkaTemplate;
    }

    @Autowired
    public void setMessage(KafkaTemplate<String, String> message) {
        this.message = message;
    }

    public ResponseEntity<Map<String, String>> verifyUserKyc(Long kycId, Long employeeId) {
        if (employeeId == null || kycId == null) {
            response.put("message", "Employee Id and User Id cannot be null");
            response.put("status", "failed");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (!employeeRepository.existsByEmployeeId(employeeId)) {
            response.put("message", "Employee does not exist");
            response.put("status", "failed");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        CompletableFuture<String> future = new CompletableFuture<>();
        futureMap.put(kycId, future);

        data.send("employeeId", new KycRequest(kycId, employeeId));

        try {
            String result = future.get(20, TimeUnit.SECONDS);
            response.put("status", "success");
            response.put("message", "KYC verification request sent successfully");
            response.put("result", result);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (TimeoutException e) {
            response.put("message", "KYC verification request timed out");
            response.put("status", "failed");
            response.put("result", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.REQUEST_TIMEOUT);
        } catch (Exception e) {
            response.put("message", "Error occurred while processing KYC verification request");
            response.put("status", "failed");
            response.put("result", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @KafkaListener(topics = "status", groupId = "users")
    public void confirmKycRequest(String message) {
        String[] parts = message.split(":");
        if (parts.length >= 2) {
            Long userId = Long.parseLong(parts[0]);
            String status = parts[1];
            String errorMessage = parts[2];

            CompletableFuture<String> future = futureMap.remove(userId);
            if (future != null) {
                if ("success".equalsIgnoreCase(status)) {
                    future.complete("KYC verification successful for user ID: " + userId);
                } else {
                    future.completeExceptionally(new Exception("KYC verification failed for user ID: " + userId + " " + errorMessage));
                }
            }
        }
    }
}
