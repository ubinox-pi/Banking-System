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


import com.neptunebank.user_service.DTO.userDto.UserAdminDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class UserService {

    private KafkaTemplate<String, KycRequest> data;
    private KafkaTemplate<String, String> message;
    private KafkaTemplate<String, String> mail;
    private UserRepository userRepository;
    private KycRepository kycRepository;

    @Autowired
    public void setMail(KafkaTemplate<String, String> mail) {
        this.mail = mail;
    }

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
    public ResponseEntity<?> registerUser(
            UsersRequestDTO request,
            MultipartFile aadhaar,
            MultipartFile pan,
            MultipartFile photo,
            MultipartFile signature,
            MultipartFile voterId,
            MultipartFile passportId,
            MultipartFile drivingLicenseId

    ) throws Exception {
        Map<String, String> response = new HashMap<>();
        if (request == null || request.getContactDetails() == null || request.getNominee() == null) {
            throw new UserException("User, contact details and nominee cannot be null.");
        }

        if (request.getMaritalStatus() == MaritalStatus.MARRIED && request.getSpouseName() == null) {
            throw new UserException("Spouse name is required for married users.");
        }
        if (!aadhaar.isEmpty() && !isValidImageFile(aadhaar)) {
            response.put("message", "Invalid Aadhaar image");
            response.put("status", "failed");
            response.put("error", "Aadhaar image must be a valid JPEG or PNG file.");
            response.put("code", "400");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if (!pan.isEmpty() && !isValidImageFile(pan)) {
            response.put("message", "Invalid PAN image");
            response.put("status", "failed");
            response.put("error", "PAN image must be a valid JPEG or PNG file.");
            response.put("code", "400");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if (!photo.isEmpty() && !isValidImageFile(photo)) {
            response.put("message", "Invalid User Photo");
            response.put("status", "failed");
            response.put("error", "User photo must be a valid JPEG or PNG file.");
            response.put("code", "400");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if (!signature.isEmpty() && !isValidImageFile(signature)) {
            response.put("message", "Invalid User Signature");
            response.put("status", "failed");
            response.put("error", "User signature must be a valid JPEG or PNG file.");
            response.put("code", "400");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if (voterId != null && !voterId.isEmpty() && !isValidImageFile(voterId)) {
            response.put("message", "Invalid Voter ID image");
            response.put("status", "failed");
            response.put("error", "Voter ID image must be a valid JPEG or PNG file.");
            response.put("code", "400");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if (passportId != null && !passportId.isEmpty() && !isValidImageFile(passportId)) {
            response.put("message", "Invalid Passport image");
            response.put("status", "failed");
            response.put("error", "Passport image must be a valid JPEG or PNG file.");
            response.put("code", "400");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if (drivingLicenseId != null && !drivingLicenseId.isEmpty() && !isValidImageFile(drivingLicenseId)) {
            response.put("message", "Invalid Driving License image");
            response.put("status", "failed");
            response.put("error", "Driving License image must be a valid JPEG or PNG file.");
            response.put("code", "400");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
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

        if (userRepository.existsByMobileNumber(users.getContactDetails().getMobileNumber())) {
            response.put("message", "Mobile number already exists");
            response.put("status", "failed");
            response.put("error", "Mobile number already exists.");
            response.put("code", "400");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(users.getContactDetails().getEmail())) {
            response.put("message", "Mail Id already exists");
            response.put("status", "failed");
            response.put("error", "Mobile number already exists.");
            response.put("code", "400");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByAadhaarNumber(users.getKycId().getAadhaarNumber())) {
            response.put("message", "Aadhaar number already exists");
            response.put("status", "failed");
            response.put("error", "Aadhaar number already exists.");
            response.put("code", "400");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByPanNumber(users.getKycId().getPanNumber())) {
            response.put("message", "PAN number already exists");
            response.put("status", "failed");
            response.put("error", "PAN number already exists.");
            response.put("code", "400");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if (users.getKycId().getVoterId() != null && !users.getKycId().getVoterId().isEmpty() && !users.getKycId().getVoterId().isBlank())
            if (userRepository.existsByVoterId(users.getKycId().getVoterId())) {
                response.put("message", "Voter ID already exists");
                response.put("status", "failed");
                response.put("error", "Voter ID already exists.");
                response.put("code", "400");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

        if (users.getKycId().getPassportNumber() != null && !users.getKycId().getPassportNumber().isEmpty() && !users.getKycId().getPassportNumber().isBlank())
            if (userRepository.existsByPassportNumber(users.getKycId().getPassportNumber())) {
                response.put("message", "Passport number already exists");
                response.put("status", "failed");
                response.put("error", "Passport number already exists.");
                response.put("code", "400");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

        if (users.getKycId().getDrivingLicenseNumber() != null && !users.getKycId().getDrivingLicenseNumber().isEmpty() && !users.getKycId().getDrivingLicenseNumber().isBlank())
            if (userRepository.existsByDrivingLicenseNumber(users.getKycId().getDrivingLicenseNumber())) {
                response.put("message", "Driving License number already exists");
                response.put("status", "failed");
                response.put("error", "Driving License number already exists.");
                response.put("code", "400");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

        if (Objects.equals(users.getContactDetails().getMobileNumber(), users.getNominee().getNomineeMobileNumber())
                || Objects.equals(users.getContactDetails().getEmail(), users.getNominee().getNomineeEmail())
                || Objects.equals(users.getKycId().getAadhaarNumber(), users.getNominee().getNomineeAadhaar())
                || Objects.equals(users.getKycId().getPanNumber(), users.getNominee().getNomineePan())) {
            response.put("message", "Nominee details cannot be same as user details");
            response.put("status", "failed");
            response.put("error", "Nominee details cannot be same as user details.");
            response.put("code", "400");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if ((Objects.equals(users.getContactDetails().getEmail(), users.getContactDetails().getAlternateEmail()) ||
                (Objects.equals(users.getContactDetails().getMobileNumber(), users.getContactDetails().getAlternateMobileNumber())))) {
            response.put("message", "Alternate contact details cannot be same as primary contact details");
            response.put("status", "failed");
            response.put("error", "Alternate contact details cannot be same as primary contact details.");
            response.put("code", "400");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            userRepository.save(users);
            String registrationEmail =
                    "Dear " +
                            users.getFirstName() + " " +
                            (users.getMiddleName() == null ? "" : users.getMiddleName() + " ") +
                            users.getLastName() + ",\n\n" +
                            "We are pleased to inform you that your registration with Neptune Bank has been successfully completed.\n\n" +
                            "Our team will now review your application, and you can expect approval within 3 business days. Once your account is approved, you will receive a confirmation email with further instructions to get started.\n\n" +
                            "What's next?\n" +
                            "- Your application is currently under verification.\n" +
                            "- We will notify you immediately once the review process is complete.\n\n" +
                            "If you have any questions in the meantime, feel free to reach out to our support team at ashish23481@gmail.com.\n\n" +
                            "Thank you for choosing Neptune Bank. We look forward to serving you.\n\n" +
                            "Warm regards,\n" +
                            "Ashish kushwaha\n" +
                            "Creator\n" +
                            "Neptune Bank\n" +
                            "neptunebank.online\n" +
                            "ashish23481@gmail.com";

            String finalMail = users.getContactDetails().getEmail() + ":Welcome to Neptune Bank – Registration Successful:" + registrationEmail;
            mail.send("send_mail_message", finalMail);
            response.put("message", "User registered successfully");
            response.put("status", "success");
            response.put("code", "201");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public ResponseEntity<?> getAllUsers() {
        List<Users> users = userRepository.getAllUsers();
        List<UserAdminDTO> userAdminDTOs = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();
        for (Users user : users) {
            userAdminDTOs.add(UsersMapper.toAdminDTO(user));
        }
        if (userAdminDTOs.isEmpty()) {
            response.put("message", "No users found");
            response.put("status", "failed");
            response.put("code", "404");
            response.put("users", userAdminDTOs);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.put("message", "Users found");
        response.put("status", "success");
        response.put("code", "200");
        response.put("users", userAdminDTOs);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @KafkaListener(topics = "employeeId", groupId = "users")
    public void setEmployee(KycRequest employee, Acknowledgment acknowledgment) {
        Kyc kyc = kycRepository.findKycByUserId(employee.getKycId());
        kyc.setVerifiedByEmployeeId(employee.getEmployeeId());
        try {
            kycRepository.save(kyc);
            message.send("status", employee.getKycId() + ":success");
            acknowledgment.acknowledge();
        } catch (Exception e) {
            message.send("status", employee.getKycId() + ":failed:" + e.getMessage());
        }
    }

    public ResponseEntity<?> userCount() {
        Long count = userRepository.countUsers();
        Map<String, String> response = new HashMap<>();
        if (count == 0) {
            response.put("message", "No users found");
            response.put("status", "failed");
            response.put("code", "404");
            response.put("count", "0");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.put("message", "users found");
        response.put("status", "success");
        response.put("code", "200");
        response.put("count", count.toString());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<?> checkUserExists(String email, String phone) {
        Map<String, String> response = new HashMap<>();
        if (email == null || phone == null) {
            response.put("message", "Email and phone number cannot be null");
            response.put("status", "failed");
            response.put("error", "Email and phone number cannot be null.");
            response.put("code", "400");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if (userRepository.checkUserExists(email, phone)) {
            response.put("message", "User exists");
            response.put("status", "Failed");
            response.put("code", "200");
            response.put("error", "User with the provided email or phone number already exists.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "User does not exist");
            response.put("status", "Success");
            response.put("code", "200");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    public boolean isAllowedImageType(MultipartFile file) {
        if (file == null || file.isEmpty()) return false;

        String contentType = file.getContentType();
        return contentType != null && (
                contentType.equalsIgnoreCase("image/jpeg") ||
                        contentType.equalsIgnoreCase("image/jpg") ||
                        contentType.equalsIgnoreCase("image/png")
        );
    }

    public boolean isAllowedImageByMagicNumber(MultipartFile file) {
        if (file == null || file.isEmpty()) return false;
        try (InputStream is = file.getInputStream()) {
            byte[] header = new byte[8];
            int b = is.read(header);

            if (header[0] == (byte) 0xFF && header[1] == (byte) 0xD8) {
                return true;
            }

            return header[0] == (byte) 0x89 && header[1] == (byte) 0x50 &&
                    header[2] == (byte) 0x4E && header[3] == (byte) 0x47;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean hasAllowedImageExtension(MultipartFile file) {
        if (file == null || file.isEmpty()) return false;
        String name = file.getOriginalFilename();
        if (name == null) return false;
        String extension = name.substring(name.lastIndexOf('.') + 1).toLowerCase();
        return extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png");
    }

    public boolean isValidImageFile(MultipartFile file) {
        return isAllowedImageType(file) && isAllowedImageByMagicNumber(file) && hasAllowedImageExtension(file);
    }

    @KafkaListener(topics = "set-account", groupId = "users")
    private void setAccountNumber(String message, Acknowledgment ack) {
        String[] split = message.split(":");
        Long userId = Long.parseLong(split[0]);
        Long accountId = Long.parseLong(split[1]);
        Users users = userRepository.findUsersByUserid(userId);
        users.getAccountId().add(accountId);
        try {
            userRepository.save(users);
            ack.acknowledge();
        } catch (Exception e) {
            // TODO: TO BE HANDLED
        }
    }


}
