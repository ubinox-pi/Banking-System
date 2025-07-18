package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

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

    @Column(nullable = false)
    private Long accountNumber;

    @Column(nullable = false, unique = true)
    private String cardNumber;

    @Column(nullable = false)
    @Size(min = 1, max = 64)
//    @Pattern(regexp = "{a-zA-Z}")
    private String cardName;

}
