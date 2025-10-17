package com.neptunebank.card_service.repositories;

import com.neptunebank.card_service.ENUM.CardStatus;
import com.neptunebank.card_service.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.card_service.repositories
 * Created by: Ashish Kushwaha on 03-09-2025 20:28
 * File: CardRepository
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END FROM Card c WHERE c.cardNumber = ?1")
    boolean existsByCardNumber(String cardNumber);

    @Query("SELECT c FROM Card c WHERE c.cardNumber = ?1")
    Card findByCardNumber(String cardNumber);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END FROM Card c WHERE c.accountNumber = ?1 AND c.status = ?2")
    boolean isCardAlreadyExistsByAccountNumber(String cardNumber, CardStatus status);

}
