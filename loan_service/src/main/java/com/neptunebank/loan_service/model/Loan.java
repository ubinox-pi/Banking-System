package com.neptunebank.loan_service.model;

import com.neptunebank.loan_service.ENUM.LoanType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.loan_service.model
 * Created by: Ashish Kushwaha on 14-07-2025 14:18
 * File: Loan
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Loan {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanId;

    @Column(nullable = false)
    private Long accountId;

    private Long approvedBy;

    @Column(nullable = false, unique = true)
    private String loanAccountNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LoanType loanType;

    @Column(nullable = false)
    private String principalAmount;

    @Column(nullable = false)
    private String interestRate;

    @Column(nullable = false)
    private String tenure;

    @Column(nullable = false)
    private String monthlyEmi;

    private LocalDateTime emiStartDate;

    private LocalDateTime nextEmiDate;

    private LocalDateTime emiEndDate;

    @Column(nullable = false)
    private String totalAmountPayable;

    @Column(nullable = false)
    private String totalInterestPayable;

    private String remainingBalance;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    private void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    private void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
