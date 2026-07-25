package com.neptunebank.messaging_service.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.user_service.exception.usersException.entity
 * Created by: Ashish Kushwaha on 25-05-2025 20:39
 * File: OtpRecord
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
@Table(indexes = {
        @Index(name = "idx_email_or_otp", columnList = "emailOrPhone"),
        @Index(name = "idx_otp", columnList = "otp")
})
public class OtpRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String emailOrPhone;
    private String otp;
    private LocalDateTime createdAt;
    @Builder.Default
    private boolean isUsed = false;
    @Builder.Default
    private boolean isOtpExpired = false;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
