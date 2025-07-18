package com.neptunebank.neptunebank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.neptunebank.controller
 * Created by: Ashish Kushwaha on 14-07-2025 20:15
 * File: WebAuthController
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
@Controller
@RequestMapping("/web/auth")
public class WebAuthController {

    private AuthenticationManager authenticationManager;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/login")
    public String webLogin() {
        return "login";
    }

    @GetMapping("/logout")
    public String webLogout() {
        return "logout";
    }

    @GetMapping("/get-access")
    @ResponseBody
    public ResponseEntity<?> getAccess(@RequestParam String username, @RequestParam String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        Map<String, String> response = new HashMap<>();
        if (authentication.isAuthenticated()) {
            response.put("status", "success");
            response.put("message", "Authentication successful");
            response.put("username", username);
            response.put("JSessionID", authentication.getName());
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }

        response.put("status", "failed");
        response.put("message", "Authentication failed. Please check your credentials.");
        response.put("username", username);
        response.put("JSessionID", "N/A");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

}
