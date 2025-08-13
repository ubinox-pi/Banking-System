package com.neptunebank.auth_service.repository;

import com.neptunebank.auth_service.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.auth_service.repository
 * Created by: Ashish Kushwaha on 19-07-2025 17:31
 * File: UserRepository
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
public interface UserRepository extends JpaRepository<Users, Long> {
    @Query("SELECT U FROM Users U WHERE U.username = ?1")
    Optional<Users> findByUsername(String username);
}
