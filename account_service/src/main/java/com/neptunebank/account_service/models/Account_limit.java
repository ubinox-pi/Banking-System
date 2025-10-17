package com.neptunebank.account_service.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.account_service.models
 * Created by: Ashish Kushwaha on 23-08-2025 17:25
 * File: Limits
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account_limit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long limitId;

    @OneToOne(mappedBy = "accountLimit")
    private Account account;

    @Builder.Default
    private Long dailyLimit = 100000L;

    @Builder.Default
    private Long remainingDailyLimit = 100000L;

    @Builder.Default
    private Long monthlyLimit = 1000000L;

    @Builder.Default
    private Long remainingMonthlyLimit = 1000000L;

    @Builder.Default
    private Long rtgsDailyLimit = 200000L;

    @Builder.Default
    private Long remainingRtgsDailyLimit = 200000L;

    @Builder.Default
    private Long rtgsMonthlyLimit = 2000000L;

    @Builder.Default
    private Long remainingRtgsMonthlyLimit = 2000000L;

    @Builder.Default
    private Long impsDailyLimit = 50000L;

    @Builder.Default
    private Long remainingImpsDailyLimit = 50000L;

    @Builder.Default
    private Long impsMonthlyLimit = 500000L;

    @Builder.Default
    private Long remainingImpsMonthlyLimit = 500000L;

    @Builder.Default
    private Long neftDailyLimit = 150000L;

    @Builder.Default
    private Long remainingNeftDailyLimit = 150000L;

    @Builder.Default
    private Long neftMonthlyLimit = 1500000L;

    @Builder.Default
    private Long remainingNeftMonthlyLimit = 1500000L;

    @Builder.Default
    private Long upiDailyLimit = 200000L;

    @Builder.Default
    private Long remainingUpiDailyLimit = 200000L;

    private LocalDateTime createdAt;
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
