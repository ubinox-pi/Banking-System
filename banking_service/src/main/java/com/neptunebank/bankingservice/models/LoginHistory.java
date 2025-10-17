package com.neptunebank.bankingservice.models;

import com.neptunebank.bankingservice.ENUMs.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDateTime;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.bankingservice.models
 * Created by: Ashish Kushwaha on 02-06-2025 12:38
 * File: LoginHistory
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
public class LoginHistory {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loginHistoryId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bank_id", nullable = false)
    private Banking banking;

    @Column
    private LocalDateTime logoutTime;

    @Column(nullable = false)
    @PastOrPresent
    private LocalDateTime loginTime;

    @Column(nullable = false)
    private String ipAddress;

    @Column(length = 100)
    private String deviceInfo;

    @Column(length = 100)
    private String location;

    @Column(nullable = false, length = 200)
    private String failureReason;

    @Column(nullable = false)
    private Boolean isSuspicious;

    @Column
    private String mfaMethodUsed;

    @Enumerated(EnumType.STRING)
    private Status status;
}
