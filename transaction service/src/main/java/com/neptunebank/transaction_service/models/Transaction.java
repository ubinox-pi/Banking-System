package com.neptunebank.transaction_service.models;

import com.neptunebank.transaction_service.ENUM.ModeOfTransaction;
import com.neptunebank.transaction_service.ENUM.TransactionMedium;
import com.neptunebank.transaction_service.ENUM.TransactionStatus;
import com.neptunebank.transaction_service.ENUM.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.LocalDateTime;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.transaction_service.models
 * Created by: Ashish Kushwaha on 24-06-2025 21:04
 * File: Transaction
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
@Component
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(length = 64)
    private String transactionId;

    @Column(length = 64, nullable = false)
    private Long sourceAccount;

    @Column(length = 64, nullable = false)
    private Long destinationAccount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ModeOfTransaction modeOfTransaction;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionMedium transactionMedium;

    @Column(nullable = false)
    private BigInteger amount;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private SourceOrDestinationBank sourceOrDestinationBankId;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String remarks;

    private LocalDateTime transactionDate;

    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @PrePersist
    public void prePersist() {
        this.transactionDate = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.transactionStatus = TransactionStatus.valueOf("PENDING");
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
        if (this.transactionStatus == null) {
            this.transactionStatus = TransactionStatus.valueOf("PENDING");
        }
    }

}

