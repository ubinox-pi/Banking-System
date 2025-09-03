package com.asp.transactionservice.models;

import com.asp.transactionservice.enumeration.ModeOfTransaction;
import com.asp.transactionservice.enumeration.TransactionMedium;
import com.asp.transactionservice.enumeration.TransactionStatus;
import com.asp.transactionservice.enumeration.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.LocalDateTime;

/*
 * Copyright (c) 2025 Ayshi Shannidhya Panda. All rights reserved.
 *
 * This source code is confidential and intended solely for internal use.
 * Unauthorized copying, modification, distribution, or disclosure of this
 * file, via any medium, is strictly prohibited.
 *
 * Project: Neptune Bank
 * Author: Ayshi Shannidhya Panda
 * Created on: 02-09-2025
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
        LocalDateTime now = LocalDateTime.now();
        this.transactionDate = now;
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }


}
