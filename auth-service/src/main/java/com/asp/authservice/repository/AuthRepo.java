package com.asp.authservice.repository;

import com.asp.authservice.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
 * Copyright (c) 2025 Ayshi Shannidhya Panda. All rights reserved.
 *
 * This source code is confidential and intended solely for internal use.
 * Unauthorized copying, modification, distribution, or disclosure of this
 * file, via any medium, is strictly prohibited.
 *
 * Project: Neptune Bank
 * Author: Ayshi Shannidhya Panda
 * Created on: 29-08-2025
 */
@Repository
public interface AuthRepo extends JpaRepository<Users, Long> {

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM Users u WHERE u.username = ?1 AND u.password = ?2")
    boolean checkUsernameAndPassword(String username, String password);

    @Query("SELECT u FROM Users u WHERE u.username = ?1")
    Optional<Users> findByUsername(String username);

}
