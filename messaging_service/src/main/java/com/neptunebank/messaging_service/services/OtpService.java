package com.neptunebank.messaging_service.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neptunebank.messaging_service.models.POJO.PhoneOrEmailAndOtp;
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
 * Package: com.neptunebank.otp_service.services
 * Created by: Ashish Kushwaha on 23-06-2025 12:55
 * File: OtpService
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
public class OtpService {
    private MailService mailService;
    private PhoneService phoneService;

    @Autowired
    public void setPhoneService(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    public ResponseEntity<Map<String, String>> authenticateEmail(String email) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map map = mapper.readValue(email, Map.class);
        String emailId = map.get("email").toString();
        return mailService.sendOtp(emailId);
    }

    public ResponseEntity<Map<String, String>> verifyEmailOtp(PhoneOrEmailAndOtp phoneOrEmailAndOtp) {
        return mailService.verifyOtp(phoneOrEmailAndOtp);
    }

    public ResponseEntity<Map<String, String>> authenticatePhone(String phone) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map map = mapper.readValue(phone, Map.class);
        phone = map.get("phone").toString();
        return phoneService.sendOtp(phone);
    }

    public ResponseEntity<Map<String, String>> verifyPhoneOtp(PhoneOrEmailAndOtp phoneOrEmailAndOtp) {
        return phoneService.verifyOtp(phoneOrEmailAndOtp.getOtp(), phoneOrEmailAndOtp.getPhoneOrEmail());
    }

    public ResponseEntity<?> resendOtp(String phoneOrEmail) throws JsonProcessingException {
        if (phoneOrEmail.contains("@")) {
            ObjectMapper mapper = new ObjectMapper();
            Map map = mapper.readValue(phoneOrEmail, Map.class);
            String email = map.get("email").toString();
            return mailService.sendOtp(email);
        } else {
            ObjectMapper mapper = new ObjectMapper();
            Map map = mapper.readValue(phoneOrEmail, Map.class);
            String phone = map.get("phone").toString();
            return phoneService.sendOtpAgain(phone);
        }
    }
}
