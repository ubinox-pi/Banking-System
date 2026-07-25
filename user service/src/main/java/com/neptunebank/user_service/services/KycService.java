package com.neptunebank.user_service.services;

import com.neptunebank.user_service.DTO.kycDTO.KycVerificationDTO;
import com.neptunebank.user_service.models.Kyc;
import com.neptunebank.user_service.repositories.KycRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.user_service.services
 * Created by: Ashish Kushwaha on 17-08-2025 01:17
 * File: KycService
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
@RequiredArgsConstructor
public class KycService {

    private final Map<Long, String> resource = new ConcurrentHashMap<>();
    private final KycRepository kycRepository;
    private final KafkaTemplate<String, String> account;
    private final KafkaTemplate<String, String> message;

    public ResponseEntity<?> doKyc(KycVerificationDTO kyc) {
        // TODO: Add role-based access control to ensure only authorized employees can perform KYC verification
        // TODO: Add logging for audit trails
        // TODO: check if kyc is already done for user
        Map<String, Object> response = new HashMap<>();
        Kyc kycId = kycRepository.findKycByUserId(kyc.getUserId());
        if (kycId == null) {
            response.put("message", "KYC not found for user ID: " + kyc.getUserId());
            response.put("status", "error");
            response.put("error", "User KYC not found");
            response.put("code", "404");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        kycId.setUser(kycId.getUser());
        kycId.setAadhaarVerified(kyc.getAadhaarVerified());
        kycId.setPanVerified(kyc.getPanVerified());
        kycId.setUserPhotoVerified(kyc.getUserPhotoVerified());
        kycId.setUserSignatureVerified(kyc.getUserSignatureVerified());
        kycId.setVoterIdVerified(kyc.getVoterIdVerified());
        kycId.setPassportVerified(kyc.getPassportVerified());
        kycId.setDrivingLicenseVerified(kyc.getDrivingLicenseVerified());
        kycId.setVerifiedByEmployeeId(kyc.getVerifiedByEmployeeId());
        kycId.setStatus(kyc.getStatus());
        kycId.setRejectionReason(kyc.getRejectionReason());

        if (kyc.getVerifiedByEmployeeId() != 0) {
            this.resource.put(kyc.getUserId(), "null");
            message.send("check-employee", String.valueOf(kyc.getVerifiedByEmployeeId()));
            int count = 0;
            while (true) {
                if (!this.resource.get(kyc.getUserId()).equalsIgnoreCase("null")) {
                    if (!this.resource.get(kyc.getUserId()).matches("\\d+")) {
                        response.put("message", "Employee verification failed");
                        response.put("status", "error");
                        response.put("error", this.resource.get(kyc.getUserId()));
                        response.put("code", "500");
                        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                    break;
                } else {
                    count++;
                    if (20 >= count) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            response.put("message", "Failed to verify employee");
                            response.put("status", "error");
                            response.put("error", e.getMessage() == null ? "Unknown error" : e.getMessage());
                            response.put("code", "500");
                            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                        }
                    } else {
                        response.put("message", "Failed to verify employee");
                        response.put("status", "error");
                        response.put("error", "Employee verification timed out");
                        response.put("code", "500");
                        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }
            }
            String[] message = this.resource.get(kyc.getUserId()).split(":");
            if (message[0].equalsIgnoreCase("failed")) {
                response.put("message", message[1]);
                response.put("status", "error");
                response.put("error", "Employee does not exist");
                response.put("code", "404");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }


        try {
            var kycInfo = kycRepository.save(kycId);
            if ("success".equalsIgnoreCase(kyc.getStatus().toString())) {
                String message =
                        kycId.getUser() +
                                ":SAVINGS:" +
                                "ACTIVE:" +
                                "SINGLE:" +
                                kycInfo.getUser().getFirstName() + " " + kycId.getUser().getMiddleName() + " " + kycId.getUser().getLastName() + ":"
                                + kycInfo.getUser().getContactDetails().getEmail();

                String to = kycInfo.getUser().getContactDetails().getEmail();
                String subject = "Neptune Bank KYC Verification";
                String body = "Dear " + kycInfo.getUser().getFirstName() + ",\n\n" +
                        "Your KYC verification has been completed successfully.\n\n" +
                        "Your account will be created shortly.\n\n" +
                        "Thank you for using Neptune Bank.\n\n" +
                        "Regards,\n" +
                        "Neptune Bank Team";
                String finalMessage = to + ":" + subject + ":" + body;
                this.message.send("send-mail-message", finalMessage);
                this.account.send("create-account", message);
            }
            response.put("message", "KYC updated successfully");
            response.put("status", "success");
            response.put("code", "200");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Failed to update KYC");
            response.put("status", "error");
            response.put("error", e.getMessage());
            response.put("code", "500");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @KafkaListener(topics = "verify-kyc", groupId = "users")
    public void setEmployee(String message, Acknowledgment acknowledgment) {
        String[] split = message.split(":");
        if (split[0].equalsIgnoreCase("success"))
            this.resource.replace(Long.parseLong(split[1]), message);
        else if (split[0].equalsIgnoreCase("failed")) {
            this.resource.replace(Long.parseLong(split[2]), "failed " + split[1]);
        }
        acknowledgment.acknowledge();
    }
}
