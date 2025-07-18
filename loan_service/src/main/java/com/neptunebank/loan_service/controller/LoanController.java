package com.neptunebank.loan_service.controller;

import com.neptunebank.loan_service.DTO.LoanRequestDto;
import com.neptunebank.loan_service.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.loan_service.controller
 * Created by: Ashish Kushwaha on 14-07-2025 15:19
 * File: LoanController
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
@RequestMapping("loans")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoanController {

    private LoanService loanService;

    @Autowired
    public void setLoanService(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createLoan(@Valid @RequestBody LoanRequestDto dto) {
        return loanService.saveLoan(dto);
    }

    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint(CsrfToken token) {
        return ResponseEntity.ok("Loan Service is running! " +
                "CSRF Token: " + token.getToken() +
                ", Header Name: " + token.getHeaderName() +
                ", Parameter Name: " + token.getParameterName());
    }
}
