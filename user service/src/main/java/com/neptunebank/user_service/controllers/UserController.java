/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project : Neptune
 * Created by: ASUS on 22-05-2025
 * File : UserController.java
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */

package com.neptunebank.user_service.controllers;


import com.neptunebank.user_service.DTO.userDto.UsersRequestDTO;
import com.neptunebank.user_service.services.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RolesAllowed({"ADMIN", "USER", "EMPLOYEE"})
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registerUser(
            @RequestPart("user") @Valid UsersRequestDTO registrationRequest,
            // files
            @RequestPart("aadhaar") MultipartFile aadhaarFile,
            @RequestPart("pan") MultipartFile panFile,
            @RequestPart("photo") MultipartFile photoFile,
            @RequestPart("signature") MultipartFile signatureFile,

            @RequestPart(value = "voter", required = false) MultipartFile voterIdFile,
            @RequestPart(value = "passport", required = false) MultipartFile passportIdFile,
            @RequestPart(value = "driving", required = false) MultipartFile drivingLicenseFile
    ) throws Exception {
        return userService.registerUser(registrationRequest, aadhaarFile, panFile, photoFile, signatureFile, voterIdFile, passportIdFile, drivingLicenseFile);
    }

    @GetMapping("/all")
    @RolesAllowed({"ADMIN", "EMPLOYEE"})
    public ResponseEntity<?> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/get-count")
    @RolesAllowed({"ADMIN", "EMPLOYEE"})
    public ResponseEntity<?> getUserCount() {
        return userService.userCount();
    }

    @GetMapping("/check-user")
    @RolesAllowed({"ADMIN", "EMPLOYEE", "USER"})
    public ResponseEntity<?> checkUserExists(@RequestParam String email, String phone) {
        return userService.checkUserExists(email, phone);
    }

    @GetMapping("/test")
    public String test() {
        return "Hello World";
    }

}
