package com.neptunebank.account_service.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.account_service.models
 * Created by: Ashish Kushwaha on 28-07-2025 19:26
 * File: Branches
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
@Table(
        indexes = {
                @Index(name = "idx_branch_name", columnList = "branchName"),
                @Index(name = "idx_branch_code", columnList = "branchCode")
        }
)
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long branchId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "branch")
    private List<Account> account = new ArrayList<>();

    @Column(nullable = false)
    private String branchName;

    @Column(nullable = false, unique = true)
    private String branchCode;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String branchAddress;

    @Column(nullable = false)
    @PastOrPresent
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @PastOrPresent
    private LocalDateTime updatedAt;

    @PrePersist
    private void prePersist() {

        if (this.branchName != null)
            if (!this.branchName.equals(this.branchName.toUpperCase()))
                this.branchName = this.branchName.toUpperCase();

        if (this.branchCode != null)
            if (!this.branchCode.equals(this.branchCode.toUpperCase()))
                this.branchCode = this.branchCode.toUpperCase();

        if (this.branchAddress != null)
            if (!this.branchAddress.equals(this.branchAddress.toUpperCase()))
                this.branchAddress = this.branchAddress.toUpperCase();


        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    private void preUpdate() {
        updatedAt = LocalDateTime.now();

        if (this.branchName != null)
            if (!this.branchName.equals(this.branchName.toUpperCase()))
                this.branchName = this.branchName.toUpperCase();

        if (this.branchCode != null)
            if (!this.branchCode.equals(this.branchCode.toUpperCase()))
                this.branchCode = this.branchCode.toUpperCase();

        if (this.branchAddress != null)
            if (!this.branchAddress.equals(this.branchAddress.toUpperCase()))
                this.branchAddress = this.branchAddress.toUpperCase();
    }
}
