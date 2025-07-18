package com.neptunebank.neptunebank.repository;

import com.neptunebank.neptunebank.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.neptunebank.repository
 * Created by: Ashish Kushwaha on 15-07-2025 01:37
 * File: AdminRepository
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
public interface AdminRepository extends JpaRepository<Users, Long> {

    @Query("SELECT Users FROM Users WHERE Users.username = ?1")
    Users findByUsername(String username);
}
