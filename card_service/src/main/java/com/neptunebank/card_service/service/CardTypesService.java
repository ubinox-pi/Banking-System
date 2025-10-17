package com.neptunebank.card_service.service;

import com.neptunebank.card_service.model.CardTypes;
import com.neptunebank.card_service.repositories.CardTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.card_service.service
 * Created by: Ashish Kushwaha on 05-09-2025 19:22
 * File: CardTypesService
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
@Service
public class CardTypesService {

    private final CardTypeRepository cardTypeRepository;

    @Autowired
    public CardTypesService(CardTypeRepository cardTypeRepository) {
        this.cardTypeRepository = cardTypeRepository;
    }

    public ResponseEntity<?> createCardType(CardTypes card) {
        cardTypeRepository.save(card);
        return new ResponseEntity<>(card, HttpStatus.CREATED);
    }
}
