package com.neptunebank.otp_service.services;

import com.neptunebank.otp_service.models.OtpRecord;
import com.neptunebank.otp_service.models.POJO.OtpPayload;
import com.neptunebank.otp_service.models.POJO.ResendOtpRecord;
import com.neptunebank.otp_service.repositories.OtpRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

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
    private final List<ResendOtpRecord> resendOtpRecords = new ArrayList<>();
    private OtpRepository otpRepository;
    private SmsSender sender;
    private WhatsappMessages whatsappMessages;

    @Autowired
    public void setWhatsappMessages(WhatsappMessages whatsappMessages) {
        this.whatsappMessages = whatsappMessages;
    }

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

            if (!phone.matches("\\d{10}")) {
                Map<String, String> response = new HashMap<>();
                response.put("error", "Invalid phone number format");
                response.put("message", "Phone number must be 10 digits long and contain only numbers.");
                response.put("status", "failed");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            Optional<List<OtpRecord>> records = otpRepository.getRecordByDate(phone, LocalDateTime.now());
            if (records.isPresent() && records.get().size() >= 5) {
                Map<String, String> response = new HashMap<>();
                response.put("error", "Cannot send OTP");
                response.put("message", "you have exceeded the maximum number of OTPs per day. Please try again tomorrow.");
                response.put("status", "failed");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

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
                        "Dear Customer, your One-Time Password (OTP) for Neptune Bank is " + otp +
                                ". Please use this code within 15 minutes to complete your verification. Do not share this code with anyone."
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
            int use = otpRepository.useOtp(otp, emailOrPhone);
            if (use == 0) {
                response.put("error", "OTP has already been used");
                response.put("status", "failed");
                response.put("code", "400");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Invalid or expired OTP");
            response.put("status", "error");
            response.put("code", "400");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ResponseEntity<?> sendOtpAgain(String phone) {
        if (phone.length() == 10) {

            if (!phone.matches("\\d{10}")) {
                Map<String, String> response = new HashMap<>();
                response.put("error", "Invalid phone number format");
                response.put("message", "Phone number must be 10 digits long and contain only numbers.");
                response.put("status", "failed");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            Optional<List<OtpRecord>> records = otpRepository.getRecordByDate(phone, LocalDateTime.now());
            if (records.isPresent() && records.get().size() >= 5) {
                Map<String, String> response = new HashMap<>();
                response.put("error", "Cannot send OTP");
                response.put("message", "you have exceeded the maximum number of OTPs per day. Please try again tomorrow.");
                response.put("status", "failed");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            List<ResendOtpRecord> entry = resendOtpRecords.stream()
                    .filter(record -> record.getPhone().equals(phone))
                    .toList();
            if (!entry.isEmpty()) {
                Map<String, String> response = new HashMap<>();
                response.put("error", "Cannot send OTP");
                response.put("message", "You have already resent otp. please wait for 2 minutes before requesting a new OTP");
                response.put("status", "failed");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            } else {
                ResendOtpRecord doEntry = new ResendOtpRecord(phone);
                resendOtpRecords.add(doEntry);
                String otp;
                do {
                    otp = generateOtp();
                } while (otpRepository.checkOtp(otp));
                OtpRecord otpRecord = OtpRecord.builder()
                        .emailOrPhone(phone)
                        .otp(otp)
                        .build();
                otpRepository.save(otpRecord);
                OtpPayload otpPayload = new OtpPayload(
                        "91" + phone,
                        "Dear Customer, your One-Time Password (OTP) for Neptune Bank is " + otp +
                                ". Please use this code within 15 minutes to complete your verification. Do not share this code with anyone."
                );
                expireOtp(otp, phone);
                whatsappMessages.sendOtp(otpPayload);
                removeEntry(phone);
                Map<String, String> response = new HashMap<>();
                response.put("message", "Otp sent successfully to " + phone);
                response.put("status", "success");
                response.put("code", "200");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Cannot send OTP");
            response.put("message", "Invalid phone number format");
            response.put("phone", phone);
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

    @Async
    protected void removeEntry(String emailOrPhone) {
        Thread.ofVirtual().start(() -> {
            try {
                Thread.sleep(1000 * 60 * 2);
                resendOtpRecords.removeIf(record -> record.getPhone().equals(emailOrPhone));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }


}
