package com.neptunebank.otp_service.services;

import com.neptunebank.otp_service.models.OtpRecord;
import com.neptunebank.otp_service.models.POJO.OtpPayload;
import com.neptunebank.otp_service.repositories.OtpRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
 * Created by: Ashish Kushwaha on 23-06-2025 12:57
 * File: PhoneService
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
public class PhoneService {
    private OtpRepository otpRepository;
    private SmsSender sender;

    @Autowired
    public void setSender(SmsSender sender) {
        this.sender = sender;
    }

    @Autowired
    public void setOtpRepository(OtpRepository otpRepository) {
        this.otpRepository = otpRepository;
    }

    private String generateOtp() {
        return String.valueOf((int) (Math.random() * 1000000));
    }

    @Transactional
    public ResponseEntity<Map<String, String>> sendOtp(String phone) {
        if (phone.length() == 10) {
            String otp;
            if (!otpRepository.checkPhoneEmail(phone)) {
                do {
                    otp = generateOtp();
                } while (otpRepository.checkOtp(otp));
                OtpRecord otpRecord = OtpRecord.builder()
                        .emailOrPhone(phone)
                        .otp(otp)
                        .build();
                otpRepository.save(otpRecord);
                OtpPayload otpPayload = new OtpPayload(
                        phone,
                        "Your OTP for Neptune Bank is: " + otp
                );

                sender.sendSms(otpPayload);
                Map<String, String> response = new HashMap<>();
                response.put("message", "Otp sent successfully to " + phone);
                response.put("status", "success");

                expireOtp(otp, phone);

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("error", "Cannot send OTP");
                response.put("message", "Otp already sent to this phone number. please wait for 15 minutes before requesting a new OTP.");
                response.put("status", "failed");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Cannot send OTP");
            response.put("message", "Invalid phone number format");
            response.put("phone", phone);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Map<String, String>> verifyOtp(String otp, String emailOrPhone) {
        Map<String, String> response = new HashMap<>();
        if (otpRepository.checkOtp(otp, emailOrPhone)) {
            response.put("message", "OTP verified successfully");
            response.put("status", "success");
            response.put("code", "200");
            otpRepository.useOtp(otp, emailOrPhone);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Invalid or expired OTP");
            response.put("status", "error");
            response.put("code", "400");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @Async
    protected void expireOtp(String otp, String emailOrPhone) {
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
