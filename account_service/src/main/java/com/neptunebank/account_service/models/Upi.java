package com.neptunebank.account_service.models;

import com.neptunebank.account_service.ENUM.Status;
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
 * Created by: Ashish Kushwaha on 23-08-2025 16:33
 * File: Upi
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
public class Upi {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "accountId", nullable = false, unique = true)
    private Account account;

    @Column(nullable = false, unique = true)
    private String upiId;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.ACTIVE;

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
