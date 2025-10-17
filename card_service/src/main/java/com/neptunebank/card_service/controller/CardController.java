package com.neptunebank.card_service.controller;

import com.neptunebank.card_service.DTO.CardDTO;
import com.neptunebank.card_service.ENUM.CardStatus;
import com.neptunebank.card_service.service.CardService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.card_service.controller
 * Created by: Ashish Kushwaha on 04-09-2025 14:19
 * File: CardController
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
public class CardController {

    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }


    @PostMapping("/apply-for-card")
    @RolesAllowed({"EMPLOYEE", "ADMIN", "USER"})
    public ResponseEntity<?> applyForCard(@RequestBody @Valid CardDTO dto) {
        return cardService.createCard(dto);
    }

    @PostMapping("/verify-card")
    @RolesAllowed({"ADMIN", "EMPLOYEE"})
    public ResponseEntity<?> verifyCard(
            @RequestParam String cardNumber,
            @RequestParam Long employeeId,
            @RequestParam CardStatus status
    ) {
        return cardService.verifyCard(cardNumber, employeeId, status);
    }
}
