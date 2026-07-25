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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
@Tag(name = "User management", description = "User related management operations")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Register a new user", description = "This endpoint allows the creation of a new user with various identification documents." +
            " The user must provide their details along with files for Aadhaar, PAN, photo, and signature. Voter ID, passport, and driving license are optional." +
            " The endpoint is accessible to authenticated users request from gateway with ADMIN, USER, or EMPLOYEE roles.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created"),
            @ApiResponse(responseCode = "401", description = "Request is not authenticated"),
            @ApiResponse(responseCode = "403", description = "Request is authenticated but does not have the required role"),
            @ApiResponse(responseCode = "400", description = "If required files or details are not provided and if provided details already exists."),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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

    @Operation(
            summary = "Get user details",
            description = "This endpoint retrieves the details of a user by their ID. It is accessible to authenticated users with ADMIN, EMPLOYEE, or USER roles."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User details retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Request is not authenticated"),
            @ApiResponse(responseCode = "403", description = "Request is authenticated but does not have the required role"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/all")
    @RolesAllowed({"ADMIN", "EMPLOYEE"})
    public ResponseEntity<?> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(
            summary = "Get user count",
            description = "This endpoint retrieves the total count of users in the system. It is accessible to authenticated users with ADMIN or EMPLOYEE roles."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User count retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Request is not authenticated"),
            @ApiResponse(responseCode = "403", description = "Request is authenticated but does not have the required role"),
            @ApiResponse(responseCode = "404", description = "No users found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/get-count")
    @RolesAllowed({"ADMIN", "EMPLOYEE"})
    public ResponseEntity<?> getUserCount() {
        return userService.userCount();
    }

    @Operation(
            summary = "Check if a user exists",
            description = "This endpoint checks if a user exists in the system by their email or phone number. It is accessible to authenticated users with ADMIN, EMPLOYEE, or USER roles."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User exists"),
            @ApiResponse(responseCode = "200", description = "User does not exist"),
            @ApiResponse(responseCode = "401", description = "Request is not authenticated"),
            @ApiResponse(responseCode = "403", description = "Request is authenticated but does not have the required role"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/check-user")
    @RolesAllowed({"ADMIN", "EMPLOYEE", "USER"})
    public ResponseEntity<?> checkUserExists(@RequestParam String email, String phone) {
        return userService.checkUserExists(email, phone);
    }

    @RolesAllowed({"USER", "ADMIN", "EMPLOYEE"})
    @GetMapping("/test")
    public String test() {
        return "Hello World";
    }

}
