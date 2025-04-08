
/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See LICENSE file for details.
 *
 *
 */

package com.neptuneBank.models;


import com.neptuneBank.DTOs.accountDTO.AccountDTO;
import com.neptuneBank.models.ENUM.AccountType;
import com.neptuneBank.models.ENUM.ModeOfOperation;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long accountId;

    @Valid
    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "userId", unique = true)
    private Users userId;

    @NotBlank(message = "Account number is required.")
    @Size(min = 11, max = 11, message = "Account number must be 11 digits.")
    @Column(updatable = false, unique = true)
    private String accountNumber;

    @Valid
    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "branchId", referencedColumnName = "branchId", unique = true)
    private Branch branch;

    @NotNull(message = "Account holder name is required.")
    @Builder.Default
    @Column(nullable = false, precision = 15, scale = 2)
    @PositiveOrZero
    private BigDecimal balance = BigDecimal.ZERO;


    @NotNull(message = "Account type is required.")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @NotNull(message = "Mode of operation is required.")
    @Enumerated(EnumType.STRING)
    private ModeOfOperation modeOfOperation;

    @Column(nullable = false, updatable = false)
    @FutureOrPresent
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @PastOrPresent
    private LocalDateTime updatedAt;

    private String generateAccountNumber() {
        return String.format("%011d", Math.abs(new java.util.Random().nextLong() % 1_000_000_000_00L));
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void fromDTO(AccountDTO dto) {
        this.userId = dto.getUserId();
        this.accountNumber = generateAccountNumber();
        this.branch.toDTOId(dto.getBranch());
        this.balance = dto.getBalance();
        this.accountType = dto.getAccountType();
        this.modeOfOperation = dto.getModeOfOperation();
    }

    public AccountDTO toDTO() {
        return AccountDTO.builder()
                .accountId(this.accountId)
                .userId(this.userId)
                .accountNumber(this.accountNumber)
                .branch(this.branch.toDTOId())
                .balance(this.balance)
                .accountType(this.accountType)
                .modeOfOperation(this.modeOfOperation)
                .build();
    }

    public AccountDTO toAdminDTO() {
        return AccountDTO.builder()
                .accountId(this.accountId)
                .userId(this.userId)
                .accountNumber(this.accountNumber)
                .branch(this.branch.toDTO())
                .balance(this.balance)
                .accountType(this.accountType)
                .modeOfOperation(this.modeOfOperation)
                .build();
    }

}
