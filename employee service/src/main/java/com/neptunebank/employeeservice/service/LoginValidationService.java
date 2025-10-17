package com.neptunebank.employeeservice.service;

import com.neptunebank.employeeservice.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.employeeservice.service
 * Created by: Ashish Kushwaha on 17-09-2025 19:05
 * File: LoginValidationService
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
public class LoginValidationService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final String secretKey;

    @Autowired
    public LoginValidationService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder, @Value("app.secret-key") String secretKey) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.secretKey = secretKey;
    }

    public ResponseEntity<?> validateLogin(String username, String password, String secretKey) {
        Map<String, String> response = new HashMap<>();
        if (secretKey != null && secretKey.equals(this.secretKey)) {
            if (username.isBlank() || password.isBlank()) {
                response.put("error", "Username and password cannot be blank");
                response.put("message", "Username and password cannot be blank");
                response.put("status", "failed");
                response.put("code", "400");
                return ResponseEntity.badRequest().body(response);
            }
            if (employeeRepository.existsByUsername(username)) {
                var employee = employeeRepository.getPasswordByUsername(username);
                if (passwordEncoder.matches(password, employee.getPassword())) {
                    if (employee.isAccountNonExpired()) {
                        if (employee.isAccountNonLocked()) {
                            if (employee.isCredentialsNonExpired()) {
                                if (employee.isEnabled()) {
                                    response.put("message", "Login successful.");
                                    response.put("status", "success");
                                    response.put("role", employee.getRoles().name().toUpperCase(Locale.ROOT));
                                    response.put("code", "200");
                                    return new ResponseEntity<>(response, HttpStatus.OK);
                                } else {
                                    response.put("message", "Your username is disabled.");
                                    response.put("status", "failed");
                                    response.put("code", "401");
                                    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
                                }
                            } else {
                                response.put("message", "Your username credentials are expired.");
                                response.put("status", "failed");
                                response.put("code", "401");
                                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
                            }
                        } else {
                            response.put("message", "your username is locked.");
                            response.put("status", "failed");
                            response.put("code", "401");
                            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
                        }
                    } else {
                        response.put("message", "Your username is expired.");
                        response.put("status", "failed");
                        response.put("code", "401");
                        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
                    }
                } else {
                    response.put("message", "Invalid password.");
                    response.put("status", "failed");
                    response.put("code", "401");
                    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
                }
            } else {
                response.put("message", "Invalid username.");
                response.put("status", "failed");
                response.put("code", "401");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
        } else {
            response.put("message", "Invalid secret key.");
            response.put("status", "failed");
            response.put("code", "401");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }
}
