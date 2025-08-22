package com.neptunebank.bankingservice.repositories;

import com.neptunebank.bankingservice.models.Banking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.bankingservice
 * Created by: Ashish Kushwaha on 20-08-2025 16:41
 * File: BankingRepository
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
public interface BankingRepository extends JpaRepository<Banking, Long> {

    @Query("SELECT CASE WHEN COUNT(U) > 0 THEN TRUE ELSE FALSE END FROM Banking U WHERE U.username = ?1 AND U.password = ?2")
    boolean findUsername(String username, String password);

    @Query("SELECT CASE WHEN COUNT(B) > 0 THEN TRUE ELSE FALSE END FROM Banking B WHERE B.username = ?1")
    boolean findUsername(String username);

    @Query("SELECT B FROM Banking B WHERE B.username = ?1")
    Banking findByUsername(String username);

    @Query("SELECT CASE WHEN COUNT(B) > 0 THEN TRUE ELSE FALSE END FROM Banking B WHERE B.userId = ?1")
    Boolean existsByUserId(Long userId);
}
