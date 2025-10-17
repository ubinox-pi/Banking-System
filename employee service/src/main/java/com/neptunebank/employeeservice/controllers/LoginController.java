package com.neptunebank.employeeservice.controllers;

import com.neptunebank.employeeservice.service.LoginValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.employeeservice.controllers
 * Created by: Ashish Kushwaha on 17-09-2025 19:03
 * File: LoginController
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
@RestController
@RequestMapping("/auth")
public class LoginController {

    private final LoginValidationService loginValidationService;

    @Autowired
    public LoginController(LoginValidationService loginValidationService) {
        this.loginValidationService = loginValidationService;
    }

    @GetMapping("/login/{username}/{password}/{secretKey}")
    public ResponseEntity<?> login(@PathVariable String username, @PathVariable String password, @PathVariable String secretKey) {
        return loginValidationService.validateLogin(username, password, secretKey);
    }
}
