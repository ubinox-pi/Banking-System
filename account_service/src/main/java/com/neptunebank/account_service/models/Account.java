package com.neptunebank.account_service.models;

import com.neptunebank.account_service.ENUM.AccountType;
import com.neptunebank.account_service.ENUM.ModeOfOperation;
import com.neptunebank.account_service.ENUM.Status;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
@Table(
        indexes = {
                @Index(name = "idx_account_user_id", columnList = "user_id"),
                @Index(name = "idx_account_branch_id", columnList = "branch_id")
        }
)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(nullable = false, updatable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "account", optional = false)
    private Upi upi;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    @JoinColumn(name = "limit_id", nullable = false, unique = true)
    private Account_limit accountLimit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    @Column(nullable = false, updatable = false, unique = true)
    private String accountNumber;

    @Column(precision = 19, scale = 2, nullable = false)
    @Builder.Default
    private BigDecimal balance = BigDecimal.valueOf(50000L);

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Status status = Status.PENDING_VERIFICATION;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ModeOfOperation modeOfOperation;

    @Column(precision = 7, scale = 4, nullable = false)
    private BigDecimal accountInterestRate;

    @Builder.Default
    private String rejectionReason = "";

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    private void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        if (rejectionReason != null && !rejectionReason.isBlank() && !rejectionReason.equals(rejectionReason.toUpperCase()))
            rejectionReason = rejectionReason.toUpperCase();
    }

    @PreUpdate
    private void preUpdate() {
        this.updatedAt = LocalDateTime.now();

        if (rejectionReason != null && !rejectionReason.isBlank() && !rejectionReason.equals(rejectionReason.toUpperCase()))
            rejectionReason = rejectionReason.toUpperCase();
    }
}
