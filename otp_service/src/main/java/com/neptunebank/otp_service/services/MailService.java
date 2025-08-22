package com.neptunebank.otp_service.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.neptunebank.otp_service.models.OtpRecord;
import com.neptunebank.otp_service.models.POJO.PhoneOrEmailAndOtp;
import com.neptunebank.otp_service.repositories.OtpRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
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
    public ResponseEntity<Map<String, String>> sendOtp(String email) throws JsonProcessingException {
        if (email.contains("@")) {

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
            message.setText("Dear Customer, your One-Time Password (OTP) for Neptune Bank is " + otp +
                    ". Please use this code within 15 minutes to complete your verification. Do not share this code with anyone.");
            javaMailSender.send(message);

            // Prepare response
            HashMap<String, String> H = new HashMap<>();
            H.put("message", "OTP sent successfully to " + email);
            H.put("status", "success");
            H.put("code", "200");

            expireOtp(otp, email);

            return new ResponseEntity<>(H, HttpStatus.CREATED);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Invalid email format");
            response.put("status", "error");
            response.put("code", "400");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @KafkaListener(topics = "send-mail-message", groupId = "users")
    public ResponseEntity<?> sendMessage(String message, Acknowledgment acknowledgment) {
        Map<String, String> response = new HashMap<>();
        String[] parts = message.split(":");
        if (parts.length < 3) {
            response.put("error", "Invalid message format. Expected format: 'to:subject:text'");
            response.put("status", "error");
            response.put("code", "400");
            response.put("message", "Invalid message format. Expected format: 'to:subject:text'");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        String email = parts[0] = parts[0].trim();
        String subject = parts[1] = parts[1].trim();
        String text = parts[2] = parts[2].trim();

        try {
            SimpleMailMessage sender = new SimpleMailMessage();
            sender.setTo(email);
            sender.setSubject(subject);
            sender.setText(text);
            javaMailSender.send(sender);
        } catch (Exception e) {
            response.put("error", "Failed to send email: " + e.getMessage());
            response.put("status", "error");
            response.put("code", "500");
            response.put("message", "Failed to send email");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            acknowledgment.acknowledge();
        }

        response.put("message", "Email sent successfully");
        response.put("status", "success");
        response.put("code", "200");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Map<String, String>> verifyOtp(PhoneOrEmailAndOtp phoneOrEmailAndOtp) {
        Map<String, String> response = new HashMap<>();
        if (otpRepository.checkOtp(phoneOrEmailAndOtp.getOtp(), phoneOrEmailAndOtp.getPhoneOrEmail())) {
            response.put("message", "OTP verified successfully");
            response.put("status", "success");
            response.put("code", "200");
            int use = otpRepository.useOtp(phoneOrEmailAndOtp.getOtp(), phoneOrEmailAndOtp.getPhoneOrEmail());
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

    @KafkaListener(topics = "send_mail_message", groupId = "users")
    public void sendMessageMail(String mail, Acknowledgment acknowledgment) {

        try {
            String[] parts = mail.split(":");
            if (parts.length < 3) {
                throw new IllegalArgumentException("Invalid message format. Expected format: 'to:subject:text'");
            }
            String to = parts[0].trim();
            String subject = parts[1].trim();
            String text = parts[2].trim();

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            javaMailSender.send(message);
            acknowledgment.acknowledge();
        } catch (Exception e) {
            // TODO: complete this method body
        }
    }

}
