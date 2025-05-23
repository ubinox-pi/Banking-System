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
import com.neptunebank.user_service.repositories.KycRepository;
import com.neptunebank.user_service.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserService {

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

    @Transactional
    public void registerUser(UsersRequestDTO request) throws UserException {
        if (request == null || request.getContactDetails() == null || request.getNominee() == null) {
            throw new UserException("User, contact details and nominee cannot be null.");
        }

        if (request.getMaritalStatus() == MaritalStatus.MARRIED && request.getSpouseName() == null) {
            throw new UserException("Spouse name is required for married users.");
        }

        userRepository.save(UsersMapper.toEntity(request));
    }

    @KafkaListener(topics = "employeeId", groupId = "users")
    public void setEmployee(KycService employee) {
        Kyc kyc = kycRepository.findByUserId(employee.getKycId());
        kyc.setVerifiedByEmployeeId(employee.getEmployeeId());
        kycRepository.save(kyc);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class KycService {
        private long kycId;
        private long employeeId;
    }
}
