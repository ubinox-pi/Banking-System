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

package com.asp.userservice.controller;


import com.asp.userservice.DTO.UsersDTO.UsersRequestDTO;
import com.asp.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HashMap<String, String>> registerUser(
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
        userService.registerUser(registrationRequest, aadhaarFile, panFile, photoFile, signatureFile, voterIdFile, passportIdFile, drivingLicenseFile);
        HashMap<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully");
        response.put("status", "success");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/test")
    public String test() {
        return "Hello World";
    }

}
