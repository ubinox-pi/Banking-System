package com.neptunebank.card_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
 * Package: com.neptunebank.card_service.model
 * Created by: Ashish Kushwaha on 04-09-2025 15:54
 * File: CardTypes
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
public class CardTypes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardTypeId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cardType")
    @Builder.Default
    private List<Card> cards = new ArrayList<>();

    @Column(nullable = false, unique = true)
    @NotBlank(message = "type name is mandatory")
    private String typeName;

    @Column(nullable = false)
    @NotBlank(message = "type inspired by is mandatory")
    private String inspiredBy;

    @Column(nullable = false)
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    @NotNull(message = "features are mandatory")
    private List<String> features = new ArrayList<>();

    @Column(nullable = false)
    @NotBlank(message = "description is mandatory")
    private String description;

    @Column(nullable = false)
    @NotNull(message = "annual fee is mandatory")
    private String tagline;

    @Column(nullable = false)
    @NotNull(message = "annual fee is mandatory")
    private Double annualFee;

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
