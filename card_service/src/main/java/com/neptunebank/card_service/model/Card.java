package com.neptunebank.card_service.model;

import com.neptunebank.card_service.ENUM.CardStatus;
import com.neptunebank.card_service.ENUM.NetworkType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: model
 * Created by: Ashish Kushwaha on 17-07-2025 18:28
 * File: Card
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
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long cardId;
    
    private Long varifiedBY;

    @ManyToOne(optional = false)
    @JoinColumn(updatable = false, nullable = false)
    private CardTypes cardType;

    @Column(nullable = false, updatable = false)
    private String accountNumber;

    @Column(nullable = false, unique = true, length = 16)
    private String cardNumber;

    @Column(nullable = false)
    private String cardHolderName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NetworkType cardNetworkType;

    @Column(nullable = false)
    private LocalDate expiryDate;

    @Column(nullable = false)
    private String cvv;

    @Column(length = 4)
    private String pin;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private CardStatus cardStatus = CardStatus.INACTIVE;

    @Column(nullable = false)
    private String annualFee;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CardStatus status;

    @Column(nullable = false)
    @Builder.Default
    private Boolean contactlessEnabled = false;

    @Column(nullable = false)
    private BigDecimal dailyLimit;

    @Column(nullable = false)
    private BigDecimal dailyUsed = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal monthlyLimit;

    @Column(nullable = false)
    private BigDecimal monthlyUsed = BigDecimal.ZERO;

    @Column(nullable = false)
    private LocalDateTime issuedAt;

    private LocalDateTime lastUsedAt;

    @Builder.Default
    private Boolean isBlocked = false;

    private LocalDateTime blockedAt;

    private LocalDateTime unblockedAt;

    private String blockedReason;

    private String unblockedReason;

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
