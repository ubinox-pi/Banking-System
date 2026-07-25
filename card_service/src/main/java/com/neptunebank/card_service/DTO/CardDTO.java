package com.neptunebank.card_service.DTO;

import com.neptunebank.card_service.ENUM.NetworkType;
import com.neptunebank.card_service.model.CardTypes;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.card_service.DTO
 * Created by: Ashish Kushwaha on 03-09-2025 20:00
 * File: CardDTO
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
@Data
@Builder
public class CardDTO {

    @NotNull(message = "Account number cannot be null")
    private String accountNumber;

    @NotNull(message = "Card number cannot be null")
    @Pattern(regexp = "^[A-Za-z ]{1,64}$", message = "Card number must be 16 digits")
    private String cardHolderName;

    @NotNull(message = "Card type cannot be null")
    @Enumerated(EnumType.STRING)
    private NetworkType cardNetworkType;

    @Builder.Default
    private Boolean contactlessEnabled = false;

    @NotBlank(message = "Card type cannot be null")
    private CardTypes cardType;

    private Long varifiedBy;
}
