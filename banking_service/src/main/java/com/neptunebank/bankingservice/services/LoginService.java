package com.neptunebank.bankingservice.services;

import com.neptunebank.bankingservice.DTO.LoginDTO;
import com.neptunebank.bankingservice.ENUMs.RecoveryPhrases;
import com.neptunebank.bankingservice.ENUMs.Status;
import com.neptunebank.bankingservice.jwt.JwtUtil;
import com.neptunebank.bankingservice.models.LoginHistory;
import com.neptunebank.bankingservice.repositories.BankingRepository;
import com.neptunebank.bankingservice.repositories.HistoryRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.bankingservice.repositories
 * Created by: Ashish Kushwaha on 02-09-2025 11:06
 * File: LoginService
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
@Service
public class LoginService {

    private JwtUtil jwtUtil;
    private BankingRepository bankingRepository;
    private BankingSessionService sessionService;
    private HistoryRepository historyRepository;

    @Autowired
    public void setHistoryRepository(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Autowired
    public void setSessionService(BankingSessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Autowired
    public void setJwtUtil(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Autowired
    public void setBankingRepository(BankingRepository bankingRepository) {
        this.bankingRepository = bankingRepository;
    }

    public ResponseEntity<?> login(LoginDTO loginDTO, HttpServletRequest request) {
        Map<String, String> responseMap = new HashMap<>();
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        RecoveryPhrases recoveryPhrases = loginDTO.getRecoveryPhrase();
        String recoveryAnswer = loginDTO.getRecoveryAnswer();

        var banking = bankingRepository.findByUsername(username);
        if (banking == null) {
            responseMap.put("error", "Invalid details");
            responseMap.put("message", "Username or password is invalid");
            responseMap.put("status", "Failed");
            responseMap.put("code", "400");
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }

        if (!banking.getPassword().equals(password)) {
            var history = LoginHistory.builder()
                    .banking(banking)
                    .loginTime(LocalDateTime.now())
                    .logoutTime(LocalDateTime.now())
                    .ipAddress(request.getRemoteAddr())
                    .deviceInfo(request.getHeader("User-Agent"))
                    .location(request.getRemoteUser())
                    .failureReason("Password mismatch")
                    .isSuspicious(false)
                    .status(Status.FAILED)
                    .build();
            historyRepository.save(history);
            responseMap.put("error", "Invalid details");
            responseMap.put("message", "Username or password is invalid");
            responseMap.put("status", "Failed");
            responseMap.put("code", "400");
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }

        if (!recoveryPhrases.equals(banking.getRecoveryPhrase()) ||
                !recoveryAnswer.equalsIgnoreCase(banking.getRecoveryAnswer())) {

            var history = LoginHistory.builder()
                    .banking(banking)
                    .loginTime(LocalDateTime.now())
                    .logoutTime(LocalDateTime.now())
                    .ipAddress(request.getRemoteAddr())
                    .deviceInfo(request.getHeader("User-Agent"))
                    .location(request.getRemoteUser())
                    .failureReason("Recovery phrase or recovery answer is invalid")
                    .isSuspicious(false)
                    .status(Status.FAILED)
                    .build();
            historyRepository.save(history);
            responseMap.put("error", "Invalid details");
            responseMap.put("message", "Recovery phrase or recovery answer is invalid");
            responseMap.put("status", "Failed");
            responseMap.put("code", "400");
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }

        if (loginDTO.getMode().equalsIgnoreCase("jwt")) {
            var history = LoginHistory.builder()
                    .banking(banking)
                    .loginTime(LocalDateTime.now())
                    .logoutTime(LocalDateTime.now())
                    .ipAddress(request.getRemoteAddr())
                    .deviceInfo(request.getHeader("User-Agent"))
                    .location(request.getRemoteUser())
                    .failureReason("none")
                    .isSuspicious(false)
                    .status(Status.SUCCESSFUL)
                    .build();
            historyRepository.save(history);
            String token = jwtUtil.generateToken(banking);
            responseMap.put("message", "Login successful");
            responseMap.put("token", token);
            responseMap.put("status", "success");
            responseMap.put("code", "200");
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        } else if (loginDTO.getMode().equalsIgnoreCase("session")) {
            var history = LoginHistory.builder()
                    .banking(banking)
                    .loginTime(LocalDateTime.now())
                    .logoutTime(LocalDateTime.now())
                    .ipAddress(request.getRemoteAddr())
                    .deviceInfo(request.getHeader("User-Agent"))
                    .location(request.getRemoteUser())
                    .failureReason("none")
                    .isSuspicious(false)
                    .status(Status.SUCCESSFUL)
                    .build();
            historyRepository.save(history);
            HttpSession session = sessionService.createSession(request);
            session.setAttribute("username", banking.getUsername());
            session.setAttribute("role", "USER");
            session.setMaxInactiveInterval(600 * 3);
            responseMap.put("message", "Login successful");
            responseMap.put("status", "success");
            responseMap.put("code", "200");
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        }
        responseMap.put("error", "Invalid details");
        responseMap.put("message", "Invalid login request");
        responseMap.put("status", "Failed");
        responseMap.put("code", "401");
        return new ResponseEntity<>(responseMap, HttpStatus.UNAUTHORIZED);
    }
}
