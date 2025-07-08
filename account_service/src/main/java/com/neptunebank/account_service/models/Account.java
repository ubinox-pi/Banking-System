package com.neptunebank.account_service.models;

import com.neptunebank.account_service.ENUM.AccountStatus;
import com.neptunebank.account_service.ENUM.AccountType;
import com.neptunebank.account_service.ENUM.ModeOfOperation;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.account_service.models
 * Created by: Ashish Kushwaha on 24-06-2025 12:06
 * File: Account
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
@Builder
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long accountId;

    @Column(unique = true, nullable = false, updatable = false)
    private List<Long> userId;

    @Column(nullable = false)
    private Long branchId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    @Column(nullable = false, updatable = false, unique = true)
    private String accountNumber;

    @Builder.Default
    private BigInteger balance = BigInteger.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ModeOfOperation modeOfOperation;

    @Column(nullable = false)
    private String accountInterestRate;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    private void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    private void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
