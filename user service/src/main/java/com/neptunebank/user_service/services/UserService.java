/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project : Neptune
 * Created by: ASUS on 22-05-2025
 * File : UserService.java
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */

package com.neptunebank.user_service.services;


import com.neptunebank.user_service.DTO.userDto.UsersRequestDTO;
import com.neptunebank.user_service.ENUMs.MaritalStatus;
import com.neptunebank.user_service.exception.usersException.UserException;
import com.neptunebank.user_service.mappers.UsersMapper;
import com.neptunebank.user_service.models.Kyc;
import com.neptunebank.user_service.models.POJO.KycRequest;
import com.neptunebank.user_service.models.Users;
import com.neptunebank.user_service.repositories.KycRepository;
import com.neptunebank.user_service.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {

    private KafkaTemplate<String, KycRequest> data;
    private KafkaTemplate<String, String> message;
    private UserRepository userRepository;
    private KycRepository kycRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setKycRepository(KycRepository kycRepository) {
        this.kycRepository = kycRepository;
    }

    @Autowired
    public void setData(KafkaTemplate<String, KycRequest> kafkaTemplate) {
        this.data = kafkaTemplate;
    }

    @Autowired
    public void setMessage(KafkaTemplate<String, String> kafkaTemplate) {
        this.message = kafkaTemplate;
    }


    @Transactional
    public void registerUser(
            UsersRequestDTO request,
            MultipartFile aadhaar,
            MultipartFile pan,
            MultipartFile photo,
            MultipartFile signature,
            MultipartFile voterId,
            MultipartFile passportId,
            MultipartFile drivingLicenseId

    ) throws Exception {
        if (request == null || request.getContactDetails() == null || request.getNominee() == null) {
            throw new UserException("User, contact details and nominee cannot be null.");
        }

        if (request.getMaritalStatus() == MaritalStatus.MARRIED && request.getSpouseName() == null) {
            throw new UserException("Spouse name is required for married users.");
        }

        Users users = UsersMapper.toEntity(request);
        users.getContactDetails().setUser(users);
        users.getNominee().setUser(users);
        users.getKycId().setUser(users);
        users.getKycId().setAadhaarImage(aadhaar.getBytes());
        users.getKycId().setPanImage(pan.getBytes());
        users.getKycId().setUserPhoto(photo.getBytes());
        users.getKycId().setUserSignature(signature.getBytes());
        if (voterId != null)
            users.getKycId().setVoterIdImage(voterId.getBytes());
        if (passportId != null)
            users.getKycId().setPassportImage(passportId.getBytes());
        if (drivingLicenseId != null)
            users.getKycId().setDrivingLicenseImage(drivingLicenseId.getBytes());
        try {
            userRepository.save(users);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @KafkaListener(topics = "employeeId", groupId = "users")
    public void setEmployee(KycRequest employee) {
        Kyc kyc = kycRepository.findByUserId(employee.getKycId());
        kyc.setVerifiedByEmployeeId(employee.getEmployeeId());
        try {
            kycRepository.save(kyc);
            message.send("status", employee.getKycId() + ":success");
        } catch (Exception e) {
            message.send("status", employee.getKycId() + ":failed:" + e.getMessage());
        }
    }
}
