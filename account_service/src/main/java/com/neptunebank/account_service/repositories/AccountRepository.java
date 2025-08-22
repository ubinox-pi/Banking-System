package com.neptunebank.account_service.repositories;

import com.neptunebank.account_service.ENUM.AccountStatus;
import com.neptunebank.account_service.ENUM.AccountType;
import com.neptunebank.account_service.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.account_service.repositories
 * Created by: Ashish Kushwaha on 24-06-2025 19:36
 * File: AccountReposoitory
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
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Account a WHERE a.accountNumber = ?1")
    boolean existsByAccountNumber(String accountNumber);

    @Query("SELECT U FROM Account U WHERE U.userId = ?1")
    List<Account> findAccountByUserId(Long userId);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Account a WHERE a.userId = ?1 AND a.accountType = ?2")
    boolean existsAccountByAccountType(Long userId, AccountType accountType);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Account a WHERE a.userId = ?1 AND a.accountType = ?2 AND a.status = ?3")
    boolean existsAccountByAccountTypeAndStatus(Long userId, AccountType accountType, AccountStatus status);
}
