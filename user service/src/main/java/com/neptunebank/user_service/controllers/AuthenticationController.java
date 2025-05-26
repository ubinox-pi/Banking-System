package com.neptunebank.user_service.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.neptunebank.user_service.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.user_service.controllers
 * Created by: Ashish Kushwaha on 26-05-2025 01:09
 * File: AuthenticationController
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private AuthenticationService authenticationService;

    @Autowired
    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/sendOtpEmail")
    public ResponseEntity<Map<String, String>> sendOtpEmail(@RequestBody String email) throws JsonProcessingException {
        return authenticationService.authenticateEmail(email);
    }

    @PostMapping("/verifyOtp")
    public ResponseEntity<Map<String, String>> verifyOtp(@RequestPart String otp, @RequestPart String emailOrPhone) {
        return authenticationService.verifyOtp(otp, emailOrPhone);
    }
}
