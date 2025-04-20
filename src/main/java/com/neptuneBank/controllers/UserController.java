/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See LICENSE file for details.
 *
 *
 */

package com.neptuneBank.controllers;

import com.neptuneBank.DTOs.usersDTO.UsersRequestDTO;
import com.neptuneBank.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<HashMap<String, String>> registerUser(@RequestBody @Valid UsersRequestDTO registrationRequest) {
        userService.registerUser(registrationRequest);
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
