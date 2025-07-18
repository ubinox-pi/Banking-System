package com.neptunebank.loan_service.service;

import com.neptunebank.loan_service.DTO.LoanRequestDto;
import com.neptunebank.loan_service.ENUM.LoanType;
import com.neptunebank.loan_service.mapper.LoanMapper;
import com.neptunebank.loan_service.model.Loan;
import com.neptunebank.loan_service.repositories.LoanRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.loan_service.service
 * Created by: Ashish Kushwaha on 14-07-2025 14:45
 * File: LoanService
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
public class LoanService {

    private LoanRepository loanRepository;

    @Autowired
    public void setLoanRepository(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Transactional
    public ResponseEntity<?> saveLoan(LoanRequestDto dto) {
        Loan request = LoanMapper.toEntity(dto);
        request.setApprovedBy(null);
        request.setLoanAccountNumber(generateLoanAccountNumber());
        request.setInterestRate(setLoanInterestRate(request.getLoanType()));
        request.setMonthlyEmi(calculateMonthlyEmi(request));
        request.setEmiStartDate(null);
        request.setNextEmiDate(null);
        request.setEmiEndDate(null);
        request.setTotalAmountPayable(calculateTotalAmountPayable(request));
        request.setTotalInterestPayable(calculateTotalInterestPayable(request));
        request.setRemainingBalance(null);

        loanRepository.save(request);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Loan request saved successfully");
        response.put("status", "success");
        response.put("code", "200");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    private String generateLoanAccountNumber() {
        String loanAccountNumber;
        do {
            long randomNum = (long) (Math.random() * 1_000_000_000L);
            loanAccountNumber = "LN" + randomNum;
        } while (loanRepository.existsByLoanId(loanAccountNumber));

        return loanAccountNumber;
    }


    private String setLoanInterestRate(LoanType loanType) {
        return switch (loanType) {
            case PERSONAL -> "10.5";
            case PAY_LATER -> "35.0";
            case PROPERTY -> "8.5";
            case HOME -> "8.0";
            case AUTO -> "9.0";
            case EDUCATION -> "7.5";
            case BUSINESS -> "12.0";
            default -> throw new IllegalArgumentException("Invalid loan type: " + loanType);
        };
    }

    private String calculateMonthlyEmi(Loan loan) {
        double principal = Double.parseDouble(loan.getPrincipalAmount());
        double rate = Double.parseDouble(loan.getInterestRate()) / 100 / 12;
        int tenureMonths = Integer.parseInt(loan.getTenure()) * 12;

        double emi = (principal * rate * Math.pow(1 + rate, tenureMonths)) / (Math.pow(1 + rate, tenureMonths) - 1);
        return String.format("%.2f", emi);
    }

    private String calculateTotalAmountPayable(Loan loan) {
        double monthlyEmi = Double.parseDouble(loan.getMonthlyEmi());
        int tenureMonths = Integer.parseInt(loan.getTenure()) * 12;
        return String.format("%.2f", monthlyEmi * tenureMonths);
    }

    private String calculateTotalInterestPayable(Loan loan) {
        double totalAmountPayable = Double.parseDouble(loan.getTotalAmountPayable());
        double principal = Double.parseDouble(loan.getPrincipalAmount());
        return String.format("%.2f", totalAmountPayable - principal);
    }
}
