package com.neptunebank.user_service.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.user_service.services
 * Created by: Ashish Kushwaha on 25-05-2025 19:58
 * File: AuthenticationService
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
public class AuthenticationService {

    private MailService mailService;

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    public ResponseEntity<Map<String, String>> authenticateEmail(String email) throws JsonProcessingException {
        return mailService.sendOtp(email);
    }

    public ResponseEntity<Map<String, String>> verifyOtp(String otp, String emailOrPhone) {
        return mailService.verifyOtp(otp, emailOrPhone);
    }
}
