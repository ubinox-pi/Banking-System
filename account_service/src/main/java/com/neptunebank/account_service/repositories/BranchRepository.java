package com.neptunebank.account_service.repositories;

import com.neptunebank.account_service.models.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.account_service.repositories
 * Created by: Ashish Kushwaha on 28-07-2025 23:06
 * File: BranchRepository
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
public interface BranchRepository extends JpaRepository<Branch, Long> {
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM Branch b WHERE b.branchCode = ?1")
    Boolean existsByBranchCode(String branchCode);

    @Query("SELECT b FROM Branch b WHERE b.branchCode = ?1")
    Branch findByBranchCode(String branchCode);
}
