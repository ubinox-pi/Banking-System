package com.neptunebank.auth_service.controller;

import com.neptunebank.auth_service.jwt.JwtUtil;
import com.neptunebank.auth_service.records.AuthResponse;
import com.neptunebank.auth_service.records.LoginRequest;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.auth_service.controller
 * Created by: Ashish Kushwaha on 19-07-2025 17:20
 * File: AuthController
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
public class AuthController {

    private final JwtUtil jwtUtil;
    private final String secret;
    private final String loginURL;
    private final RestTemplate restTemplate;

    @Autowired
    public AuthController(JwtUtil jwtUtil, @Value("${app.secret-key}") String secretKey, @Value("${app.login-url}") String loginURL) {
        this.jwtUtil = jwtUtil;
        this.secret = secretKey;
        this.loginURL = loginURL;
        this.restTemplate = new RestTemplate();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest, HttpServletResponse response, HttpServletRequest request) {
        Map<String, String> responseMap = new HashMap<>();

        String username = loginRequest.username();
        String password = loginRequest.password();

        Map<String, String> urlVariables = Map.of(
                "username", username,
                "password", password,
                "secretKey", this.secret
        );

        ResponseEntity<Map> loginResponse = restTemplate.getForEntity(
                loginURL,
                Map.class,
                urlVariables
        );
        if (loginResponse.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>(loginResponse.getBody(), HttpStatus.UNAUTHORIZED);
        }
        Map res = loginResponse.getBody();

        assert res != null;
        String role = res.get("role").toString();
        if (role == null || role.isBlank()) {
            responseMap.put("status", "failed");
            responseMap.put("code", "500");
            responseMap.put("error", "Internal server error");
            responseMap.put("message", "Role not found");
            return new ResponseEntity<>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (loginResponse.getStatusCode() == HttpStatus.OK) {
            if ("session".equalsIgnoreCase(loginRequest.mode())) {
                HttpSession session = request.getSession(true);
                session.setAttribute("username", loginRequest.username());
                session.setAttribute("role", role);
                session.setMaxInactiveInterval(600 * 3);
                responseMap.put("message", "Login successful");
                responseMap.put("username", loginRequest.username());
                responseMap.put("role", role);
                responseMap.put("status", "success");
                responseMap.put("code", "200");
                return new ResponseEntity<>(responseMap, HttpStatus.OK);
            } else if ("jwt".equalsIgnoreCase(loginRequest.mode())) {
                request.getSession(false);
                String token = jwtUtil.generateToken(loginRequest.username(), role);
                responseMap.put("message", "Login successful");
                responseMap.put("username", loginRequest.username());
                responseMap.put("role", role);
                responseMap.put("token", token);
                responseMap.put("status", "success");
                responseMap.put("code", "200");
                return new ResponseEntity<>(responseMap, HttpStatus.OK);
            } else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login request");
        } else {
            responseMap.put("status", "failed");
            responseMap.put("code", "401");
            responseMap.put("error", "Unauthorized");
            responseMap.put("message", "Internal server error");
            return new ResponseEntity<>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateSession(HttpServletRequest request) {
        Map<String, String> responseMap = new HashMap<>();
        String security = request.getHeader("X-Secret-Key");
        if (security == null || !security.equals(secret)) {
            responseMap.put("message", "Invalid secret key");
            responseMap.put("status", "error");
            responseMap.put("code", "401");
            responseMap.put("error", "Unauthorized");
            return new ResponseEntity<>(responseMap, HttpStatus.UNAUTHORIZED);
        }

        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                String username = (String) session.getAttribute("username");
                String role = (String) session.getAttribute("role");
                if (username != null && role != null) {
                    return new ResponseEntity<>(new AuthResponse(username, role), HttpStatus.OK);
                } else {
                    responseMap.put("message", "Session have no attributes");
                    responseMap.put("status", "error");
                    responseMap.put("code", "401");
                    responseMap.put("error", "Unauthorized");
                    return new ResponseEntity<>(responseMap, HttpStatus.UNAUTHORIZED);
                }
            }
            String jwt = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (jwt != null && jwt.startsWith("Bearer ")) {
                Claims claims = jwtUtil.validateToken(jwt.substring(7));
                String username = claims.getSubject();
                String role = claims.get("role", String.class);
                return new ResponseEntity<>(new AuthResponse(username, role), HttpStatus.OK);
            } else {
                responseMap.put("message", "Invalid authorization header");
                responseMap.put("status", "error");
                responseMap.put("code", "401");
                responseMap.put("error", "Unauthorized");
                return new ResponseEntity<>(responseMap, HttpStatus.UNAUTHORIZED);
            }


        } catch (Throwable t) {
            responseMap.put("message", "Invalid session");
            responseMap.put("status", "error");
            responseMap.put("code", "401");
            responseMap.put("error", t.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok("Logged out successfully.");
    }
}
