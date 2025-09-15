/*
 * Copyright (c) 2025 Ayshi Shannidhya Panda. All rights reserved.
 *
 * This source code is confidential and intended solely for internal use.
 * Unauthorized copying, modification, distribution, or disclosure of this
 * file, via any medium, is strictly prohibited.
 *
 * Project: Neptune Bank
 * Author: Ayshi Shannidhya Panda
 * Created on: 20-06-2025
 */
package com.asp.userservice.service;

import com.asp.userservice.DTO.UsersDTO.UsersRequestDTO;
import com.asp.userservice.enumeration.MaritalStatus;
import com.asp.userservice.mappers.UsersMapper;
import com.asp.userservice.models.Users;
import com.asp.userservice.repositories.KycRepository;
import com.asp.userservice.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {

    private UserRepository userRepository;
    private KycRepository kycRepository;
    private UsersMapper usersMapper;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setKycRepository(KycRepository kycRepository) {
        this.kycRepository = kycRepository;
    }

    @Autowired
    public void setUsersMapper(UsersMapper usersMapper) {
        this.usersMapper = usersMapper;
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
        try {
            if (request == null || request.getContactDetails() == null || request.getNominee() == null) {
                System.out.println("User, contact details and nominee cannot be null.");
                throw new IllegalArgumentException("User, contact details and nominee cannot be null.");
            }

            if (request.getMaritalStatus() == MaritalStatus.MARRIED && request.getSpouseName() == null) {
                System.out.println("Spouse name is required for married users.");
                throw new IllegalArgumentException("Spouse name is required for married users.");
            }

            Users users = UsersMapper.toEntity(request);

            if (users.getContactDetails() == null) {
                throw new IllegalStateException("Mapped contact details are null.");
            }

            if (users.getNominee() == null) {
                throw new IllegalStateException("Mapped nominee details are null.");
            }

            if (users.getKycId() == null) {
                throw new IllegalStateException("Mapped KYC details are null.");
            }

            users.getContactDetails().setUser(users);
            users.getNominee().setUser(users);
            users.getKycId().setUser(users);

            try {
                users.getKycId().setAadharImage(aadhaar.getBytes());
            } catch (Exception e) {
                throw new Exception("Failed to process Aadhaar image", e);
            }

            try {
                users.getKycId().setPanImage(pan.getBytes());
            } catch (Exception e) {
                throw new Exception("Failed to process PAN image", e);
            }

            try {
                users.getKycId().setUserPhoto(photo.getBytes());
            } catch (Exception e) {
                throw new Exception("Failed to process photo", e);
            }

            try {
                users.getKycId().setUserSignature(signature.getBytes());
            } catch (Exception e) {
                throw new Exception("Failed to process signature", e);
            }

            // Optional docs
            if (voterId != null && !voterId.isEmpty()) {
                try {
                    users.getKycId().setVoterIdImage(voterId.getBytes());
                } catch (Exception e) {
                    throw new Exception("Failed to process voter ID image", e);
                }
            }

            if (passportId != null && !passportId.isEmpty()) {
                try {
                    users.getKycId().setPassportImage(passportId.getBytes());
                } catch (Exception e) {
                    throw new Exception("Failed to process passport image", e);
                }
            }

            if (drivingLicenseId != null && !drivingLicenseId.isEmpty()) {
                try {
                    users.getKycId().setDrivingLicenseImage(drivingLicenseId.getBytes());
                } catch (Exception e) {
                    throw new Exception("Failed to process driving license image", e);
                }
            }

            // Persist user and cascade entities
            userRepository.save(users);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("User registration failed: " + e.getMessage(), e);
        }
    }


}
