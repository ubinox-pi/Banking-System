package com.neptunebank.employeeservice.service;

import com.neptunebank.employeeservice.models.POJO.KycService;
import com.neptunebank.employeeservice.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

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

    private EmployeeRepository employeeRepository;
    private KafkaTemplate<String, KycService> data;
    private KafkaTemplate<String, String> message;

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Autowired
    public void setData(KafkaTemplate<String, KycService> kafkaTemplate) {
        this.data = kafkaTemplate;
    }

    @Autowired
    public void setMessage(KafkaTemplate<String, String> message) {
        this.message = message;
    }

    public void verifyUserKyc(Long userId, Long employeeId) {
        if (employeeId == null || userId == null) {
            throw new IllegalArgumentException("User ID and Employee ID cannot be null");
        } else if (employeeRepository.existsByEmployeeId(employeeId)) {
            KycService kycService = new KycService(userId, employeeId);
            data.send("employeeId", kycService);
        }
    }

    @KafkaListener(topics = "status", groupId = "users")
    public void confirmKyc(String status) {
        if (status.equals("success")) {
            
        }
    }
}
