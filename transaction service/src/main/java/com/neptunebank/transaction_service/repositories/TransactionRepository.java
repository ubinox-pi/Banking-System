package com.neptunebank.transaction_service.repositories;

import com.neptunebank.transaction_service.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.transaction_service.repositories
 * Created by: Ashish Kushwaha on 25-06-2025 20:54
 * File: TransactionRepository
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
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Transaction t WHERE t.transactionId = ?1")
    Boolean existsByTransactionId(String transactionId);

    @Query("SELECT t FROM Transaction t WHERE t.transactionId = ?1")
    Transaction getTransactionById(String transactionId);
}
