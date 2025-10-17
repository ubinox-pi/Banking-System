package com.neptunebank.card_service.controller;

import com.neptunebank.card_service.model.CardTypes;
import com.neptunebank.card_service.service.CardTypesService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.card_service.controller
 * Created by: Ashish Kushwaha on 05-09-2025 19:14
 * File: CardTypeController
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
@RestController
@RequestMapping("/cards")
public class CardTypeController {

    private final CardTypesService cardTypesService;

    public CardTypeController(CardTypesService cardTypesService) {
        this.cardTypesService = cardTypesService;
    }

    @PostMapping("/create-cardtype")
    @RolesAllowed({"ADMIN", "EMPLOYEE"})
    public ResponseEntity<?> createCardType(@RequestBody @Valid CardTypes cardTypes) {
        return cardTypesService.createCardType(cardTypes);
    }
}
