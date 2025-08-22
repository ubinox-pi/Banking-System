package com.neptunebank.user_service.repositories;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.user_service.repositories
 * Created by: Ashish Kushwaha on 22-05-2025 23:47
 * File: KycRepository
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */

import com.neptunebank.user_service.models.Kyc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface KycRepository extends JpaRepository<Kyc, Long> {
    @Query("SELECT k FROM Kyc k WHERE k.user = ?1")
    Kyc findKycByUserId(Long userId);
}
