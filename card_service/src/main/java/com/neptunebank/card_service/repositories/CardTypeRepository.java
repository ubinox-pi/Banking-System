package com.neptunebank.card_service.repositories;

import com.neptunebank.card_service.model.CardTypes;
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
 * Created by: Ashish Kushwaha on 05-09-2025 19:02
 * File: CardTypeRepository
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
public interface CardTypeRepository extends JpaRepository<CardTypes, Long> {

    @Query("SELECT CASE WHEN COUNT(ct) > 0 THEN TRUE ELSE FALSE END FROM CardTypes ct WHERE ct.cardTypeId = ?1")
    boolean existsCardTypesByCardTypeId(CardTypes cardTypeId);

    @Query("SELECT ct FROM CardTypes ct WHERE ct.cardTypeId = ?1")
    CardTypes findCardTypesByCardTypeId(CardTypes cardTypeId);
}
