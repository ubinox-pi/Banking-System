package com.neptunebank.employeeservice.service;

import com.neptunebank.employeeservice.DTOs.employeeDto.EmployeeRequestDTO;
import com.neptunebank.employeeservice.mappers.EmployeeMapper;
import com.neptunebank.employeeservice.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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

    private final EmployeeRepository employeeRepository;
    private final KafkaTemplate<String, String> data;
    private final KafkaTemplate<String, String> message;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, KafkaTemplate<String, String> data, KafkaTemplate<String, String> message, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.data = data;
        this.message = message;
        this.passwordEncoder = passwordEncoder;
    }

    @KafkaListener(topics = "check-employee", groupId = "users")
    private void confirmKycRequest(String message, Acknowledgment acknowledgment) {
        try {
            if (employeeRepository.existsByEmployeeId(Long.parseLong(message))) {
                this.message.send("verify-kyc", "success" + ":" + message);
                acknowledgment.acknowledge();
            } else {
                this.message.send("verify-kyc", "failed:Employee does not exist:" + message);
                acknowledgment.acknowledge();
            }
        } catch (Throwable t) {
            this.message.send("verify-kyc", "failed:" + t.getMessage() + ":" + message);
        }

    }

    @KafkaListener(topics = "check-employee-for-card", groupId = "users")
    private void confirmEmployeeForCardRequest(String message, Acknowledgment acknowledgment) {
        try {
            if (employeeRepository.existsByEmployeeId(Long.parseLong(message))) {
                this.message.send("check-employee-for-card-validation", message + ":" + true);
                acknowledgment.acknowledge();
            } else {
                this.message.send("check-employee-for-card-validation", message + ":" + false);
                acknowledgment.acknowledge();
            }
        } catch (Throwable t) {
            this.message.send("check-employee-for-card-validation", message + ":" + false);
            acknowledgment.acknowledge();
        }
    }

    public ResponseEntity<?> createEmployee(EmployeeRequestDTO dto) {
        Map<String, String> responseMap = new HashMap<>();
        var emp = EmployeeMapper.toEntiry(dto);
        if (employeeRepository.existsByUsername(emp.getUsername())) {
            responseMap.put("error", "User already exists");
            responseMap.put("status", "failed");
            responseMap.put("code", "409");
            return new ResponseEntity<>(responseMap, HttpStatus.CONFLICT);
        }
        if (employeeRepository.existsByPhoneAndEmail(emp.getMobileNumber(), emp.getEmail())) {
            responseMap.put("error", "Employee with same phone number and email already exists");
            responseMap.put("status", "failed");
            responseMap.put("code", "409");
            return new ResponseEntity<>(responseMap, HttpStatus.CONFLICT);
        }
        emp.setPassword(passwordEncoder.encode(emp.getPassword()));
        employeeRepository.save(emp);
        responseMap.put("status", "success");
        responseMap.put("code", "200");
        responseMap.put("message", "Employee created successfully");
        return new ResponseEntity<>(responseMap, HttpStatus.CREATED);
    }
}
