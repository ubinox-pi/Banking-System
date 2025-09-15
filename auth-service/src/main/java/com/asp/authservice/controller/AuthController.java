package com.asp.authservice.controller;

import com.asp.authservice.jwt.JwtUtil;
import com.asp.authservice.model.Users;
import com.asp.authservice.records.LoginRecord;
import com.asp.authservice.repository.AuthRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/*
 * Copyright (c) 2025 Ayshi Shannidhya Panda. All rights reserved.
 *
 * This source code is confidential and intended solely for internal use.
 * Unauthorized copying, modification, distribution, or disclosure of this
 * file, via any medium, is strictly prohibited.
 *
 * Project: Neptune Bank
 * Author: Ayshi Shannidhya Panda
 * Created on: 29-08-2025
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthRepo authRepo;
    private JwtUtil jwtUtil;

    @Autowired
    public void setJwtUtil(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Autowired
    public void setAuthRepo(AuthRepo authRepo) {
        this.authRepo = authRepo;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRecord loginRecord, HttpServletRequest request) {


        Map<String, String> response = new HashMap<>();

        if (loginRecord.username().isBlank() &&
                loginRecord.password().isBlank() &&
                loginRecord.mode().isBlank()) {
            response.put("error", "Login failed.");
            response.put("status", "failed");
            response.put("code", "401");
            response.put("message", "Username, password or mode is empty.");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        if (authRepo.checkUsernameAndPassword(loginRecord.username(), loginRecord.password())) {

            Users user = authRepo.findByUsername(loginRecord.username()).orElseThrow();

            if (!user.isAccountNonExpired()) {
                response.put("error", "Account is Expired");
                response.put("status", "failed");
                response.put("code", "404");
                response.put("message", "Expired");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            if (!user.isAccountNonExpired()) {
                response.put("error", "Account is Locked");
                response.put("status", "failed");
                response.put("code", "404");
                response.put("message", "Unlock it.");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            if (!user.isCredentialsNonExpired()) {
                response.put("error", "Credential is Expired");
                response.put("status", "failed");
                response.put("code", "404");
                response.put("message", "Expired");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            if (!user.isEnabled()) {
                response.put("error", "Account is Disabled");
                response.put("status", "failed");
                response.put("code", "404");
                response.put("message", "Disable");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            if (loginRecord.mode().equalsIgnoreCase("session")) {
                HttpSession httpSession = request.getSession();
                httpSession.setAttribute("username", user.getUsername());
                httpSession.setAttribute("role", user.getRole().name());
                httpSession.setMaxInactiveInterval(600 * 3);
                response.put("status", "success");
                response.put("code", "200");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else if (loginRecord.mode().equalsIgnoreCase("jwt")) {
                request.getSession(false);
                String token = jwtUtil.generateToken(user);
                response.put("message", "Login successful");
                response.put("username", user.getUsername());
                response.put("role", user.getRole().name());
                response.put("token", token);
                response.put("status", "success");
                response.put("code", "200");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {

                response.put("error", "Authentication Failed");
                response.put("status", "failed");
                response.put("code", "401");
                response.put("message", "Try Again.");

                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);

            }


        }

        return null;
    }

    @GetMapping("/validate")
    public ResponseEntity<Map<String, String>> validateSession(HttpServletRequest request) {
        String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
        Map<String, String> response = new HashMap<>();

        if (auth == null || !auth.toLowerCase().startsWith("bearer ")) {
            response.put("message", "Authorization header missing or invalid");
            response.put("code", "400");
            response.put("status", "Failure");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            Claims claims = jwtUtil.validateToken(auth.substring(7));
            String username = claims.getSubject();
            String role = claims.get("role", String.class);

            response.put("message", "Successfully Validated");
            response.put("code", "200");
            response.put("status", "Success");
            response.put("username", username);
            response.put("role", role);

            return ResponseEntity.ok(response);
        } catch (JwtException e) {
            response.put("message", "Invalid or expired token");
            response.put("code", "401");
            response.put("status", "Failure");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }


    @PostMapping("/add-user")
    public ResponseEntity<?> addUser(@RequestBody @Valid Users user) {
        Optional<Users> existingUser = authRepo.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }
        authRepo.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }

}
