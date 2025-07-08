package com.neptunebank.otp_service.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neptunebank.otp_service.models.OtpRecord;
import com.neptunebank.otp_service.models.POJO.PhoneOrEmailAndOtp;
import com.neptunebank.otp_service.repositories.OtpRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.otp_service.services
 * Created by: Ashish Kushwaha on 23-06-2025 12:58
 * File: MailService
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
public class MailService {
    private OtpRepository otpRepository;
    private JavaMailSender javaMailSender;

    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Autowired
    public void setOtpRepository(OtpRepository otpRepository) {
        this.otpRepository = otpRepository;
    }

    private String generateOtp() {
        return String.valueOf((int) (Math.random() * 1000000));
    }

    @Transactional
    public ResponseEntity<Map<String, String>> sendOtp(String phoneNumber) throws JsonProcessingException {
        if (phoneNumber.contains("@")) {

            // Assuming phoneNumber is a JSON string containing an email field
            ObjectMapper mapper = new ObjectMapper();
            Map map = mapper.readValue(phoneNumber, Map.class);
            String email = map.get("email").toString();

            // Validate email format
            String otp;
            do {
                otp = generateOtp();
            } while (otpRepository.checkOtp(otp));
            OtpRecord otpRecord = OtpRecord.builder()
                    .emailOrPhone(email)
                    .otp(otp)
                    .build();
            otpRepository.save(otpRecord);

            // Send OTP via email
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Neptune Bank OTP Verification");
            message.setText("Your OTP for " + email + " is: " + otp + ". Please do not share it with anyone.");
            javaMailSender.send(message);

            // Prepare response
            HashMap<String, String> H = new HashMap<>();
            H.put("message", "OTP sent successfully to " + phoneNumber);
            H.put("status", "success");
            H.put("code", "200");

            expire1Otp(otp, email);

            return new ResponseEntity<>(H, HttpStatus.CREATED);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Invalid email format");
            response.put("status", "error");
            response.put("code", "400");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ResponseEntity<Map<String, String>> verifyOtp(PhoneOrEmailAndOtp phoneOrEmailAndOtp) {
        Map<String, String> response = new HashMap<>();
        if (otpRepository.checkOtp(phoneOrEmailAndOtp.getOtp(), phoneOrEmailAndOtp.getPhoneOrEmail())) {
            response.put("message", "OTP verified successfully");
            response.put("status", "success");
            response.put("code", "200");
            otpRepository.useOtp(phoneOrEmailAndOtp.getOtp(), phoneOrEmailAndOtp.getPhoneOrEmail());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Invalid or expired OTP");
            response.put("status", "error");
            response.put("code", "400");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @Async
    protected void expire1Otp(String otp, String emailOrPhone) {
        Thread.ofVirtual().start(() -> {
            try {
                Thread.sleep(1000 * 60 * 15);
                otpRepository.expireOtp(otp, emailOrPhone);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
