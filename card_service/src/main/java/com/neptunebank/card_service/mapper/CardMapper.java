package com.neptunebank.card_service.mapper;

import com.neptunebank.card_service.DTO.CardDTO;
import com.neptunebank.card_service.model.Card;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.card_service.mapper
 * Created by: Ashish Kushwaha on 03-09-2025 20:22
 * File: CardMapper
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
public class CardMapper {

    public static Card toEntity(CardDTO dto) {
        return Card.builder()
                .accountNumber(dto.getAccountNumber())
                .cardHolderName(dto.getCardHolderName())
                .cardType(dto.getCardType())
                .contactlessEnabled(dto.getContactlessEnabled())
                .build();
    }
}
