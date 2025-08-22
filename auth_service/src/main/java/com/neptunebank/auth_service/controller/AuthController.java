package com.neptunebank.auth_service.controller;

import com.neptunebank.auth_service.jwt.JwtUtil;
import com.neptunebank.auth_service.models.Users;
import com.neptunebank.auth_service.records.AuthResponse;
import com.neptunebank.auth_service.records.LoginRequest;
import com.neptunebank.auth_service.repository.UserRepository;
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
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    private UserRepository userRepository;
    private JwtUtil jwtUtil;
    @Value("${secret.key}")
    private String secret;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setJwtUtil(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest, HttpServletResponse response, HttpServletRequest request) {
        Map<String, String> responseMap = new HashMap<>();
        Users users = userRepository.findByUsername(loginRequest.username())
                .filter(u -> u.getPassword().equals(loginRequest.password()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        if (users.isExpired()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User account is expired");
        }
        if (users.isLocked()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User account is locked");
        }
        if (!users.isActive()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User account is inactive");
        }
        if (users.getRoles() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User role is not assigned");
        }
        if (users.isCredentialsExpired()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User credentials are expired");
        }
        if ("session".equalsIgnoreCase(loginRequest.mode())) {
            HttpSession session = request.getSession(true);
            session.setAttribute("username", users.getUsername());
            session.setAttribute("role", users.getRoles().name());
            session.setMaxInactiveInterval(600 * 3);
            responseMap.put("message", "Login successful");
            responseMap.put("username", users.getUsername());
            responseMap.put("role", users.getRoles().name());
            responseMap.put("status", "success");
            responseMap.put("code", "200");
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        } else if ("jwt".equalsIgnoreCase(loginRequest.mode())) {
            request.getSession(false);
            String token = jwtUtil.generateToken(users);
            responseMap.put("message", "Login successful");
            responseMap.put("username", users.getUsername());
            responseMap.put("role", users.getRoles().name());
            responseMap.put("token", token);
            responseMap.put("status", "success");
            responseMap.put("code", "200");
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        } else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login request");
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateSession(HttpServletRequest request) {
        String security = request.getHeader("X-Secret-Key");
        if (security == null || !security.equals(secret)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String jwt = request.getHeader(HttpHeaders.AUTHORIZATION);

        try {
            if (jwt != null && jwt.startsWith("Bearer ")) {
                Claims claims = jwtUtil.validateToken(jwt.substring(7));
                String username = claims.getSubject();
                String role = claims.get("role", String.class);
                return new ResponseEntity<>(new AuthResponse(username, role), HttpStatus.OK);
            }
            HttpSession session = request.getSession(false);
            if (session != null) {
                String username = (String) session.getAttribute("username");
                String role = (String) session.getAttribute("role");
                if (username != null && role != null) {
                    return new ResponseEntity<>(new AuthResponse(username, role), HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok("Logged out successfully.");
    }

    @PostMapping("/add-user")
    public ResponseEntity<?> addUser(@RequestBody @Valid Users user) {
        Optional<Users> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }
}
