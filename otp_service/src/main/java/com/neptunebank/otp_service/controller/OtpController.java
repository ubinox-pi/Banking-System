package com.neptunebank.otp_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.neptunebank.otp_service.models.POJO.PhoneOrEmailAndOtp;
import com.neptunebank.otp_service.services.OtpService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.otp_service.controller
 * Created by: Ashish Kushwaha on 23-06-2025 12:53
 * File: OtpController
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
@RequestMapping("/messages/otp")
public class OtpController {
    private OtpService authenticationService;

    @Autowired
    public void setAuthenticationService(OtpService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/send-otp-email")
    public ResponseEntity<Map<String, String>> sendOtpEmail(@RequestBody String email) throws JsonProcessingException {
        return authenticationService.authenticateEmail(email);
    }

    @PostMapping("/verify-email-otp")
    public ResponseEntity<Map<String, String>> verifyOtp(@RequestBody PhoneOrEmailAndOtp phoneOrEmailAndOtp) {
        return authenticationService.verifyEmailOtp(phoneOrEmailAndOtp);
    }

    @PostMapping("/send-otp-phone")
    public ResponseEntity<Map<String, String>> sendOtpPhone(@RequestBody String phone) throws JsonProcessingException {
        return authenticationService.authenticatePhone(phone);
    }

    @PostMapping("/verify-phone-otp")
    public ResponseEntity<Map<String, String>> verifyPhoneOtp(@RequestBody @Valid PhoneOrEmailAndOtp phoneOrEmailAndOtp) {
        return authenticationService.verifyPhoneOtp(phoneOrEmailAndOtp);
    }

    @PostMapping("/resend-otp-phone")
    public ResponseEntity<?> resendOtpPhone(@RequestBody String phone) throws JsonProcessingException {
        return authenticationService.resendOtp(phone);
    }
}
