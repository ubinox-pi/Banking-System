package com.neptunebank.user_service.services;

import com.neptunebank.user_service.DTO.kycDTO.KycVerificationDTO;
import com.neptunebank.user_service.models.Kyc;
import com.neptunebank.user_service.repositories.KycRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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
public class KycService {

    private KycRepository kycRepository;
    private KafkaTemplate<String, String> account;

    @Autowired
    public void setAccount(KafkaTemplate<String, String> account) {
        this.account = account;
    }

    @Autowired
    public void setKycRepository(KycRepository kycRepository) {
        this.kycRepository = kycRepository;
    }

    public ResponseEntity<?> doKyc(KycVerificationDTO kyc) {
        Map<String, Object> response = new HashMap<>();
        Kyc kycId = kycRepository.findKycByUserId(kyc.getUserId());
        if (kycId == null) {
            response.put("message", "KYC not found for user ID: " + kyc.getUserId());
            response.put("status", "error");
            response.put("error", "User KYC not found");
            response.put("code", "404");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
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
        try {
            var kycInfo = kycRepository.save(kycId);
            if ("success".equalsIgnoreCase(kyc.getStatus().toString())) {
                String message = kycId.getUser() + ":SAVINGS:" + "ACTIVE:" +
                        "SINGLE:" +
                        kycInfo.getUser().getFirstName() + " " + kycId.getUser().getMiddleName() + " " + kycId.getUser().getLastName() + ":"
                        + kycInfo.getUser().getContactDetails().getEmail();
                account.send("create-account", message);
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
}
