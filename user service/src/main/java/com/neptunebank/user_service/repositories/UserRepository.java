/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project : Neptune
 * Created by: ASUS on 22-05-2025
 * File : UserRepository.java
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */

package com.neptunebank.user_service.repositories;

import com.neptunebank.user_service.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    @Query("SELECT u FROM Users u WHERE u.status = ?1")
    Optional<List<Users>> allUsersByStatus(String status) throws IllegalArgumentException;

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Users u WHERE u.contactDetails.mobileNumber = ?1")
    boolean existsByMobileNumber(String mobileNumber);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Users u WHERE u.contactDetails.email = ?1")
    boolean existsByEmail(String email);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Users u WHERE u.kycId.aadhaarNumber = ?1")
    boolean existsByAadhaarNumber(String aadhaarNumber);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Users u WHERE u.kycId.panNumber = ?1")
    boolean existsByPanNumber(String panNumber);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Users u WHERE u.kycId.voterId = ?1")
    boolean existsByVoterId(String voterId);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Users u WHERE u.kycId.passportNumber = ?1")
    boolean existsByPassportNumber(String passportNumber);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Users u WHERE u.kycId.drivingLicenseNumber = ?1")
    boolean existsByDrivingLicenseNumber(String drivingLicenseNumber);

    @Query("SELECT U FROM Users U")
    Optional<List<Users>> getAllUsers();

    @Query("SELECT COUNT(u) FROM Users u")
    long countUsers();

    @Query("SELECT COUNT(u) > 0 FROM Users u WHERE u.contactDetails.email = ?1 OR u.contactDetails.mobileNumber = ?2")
    boolean checkUserExists(String email, String phoneNumber);

    @Query("SELECT u FROM Users u WHERE u.userid = ?1")
    Optional<Users> findUsersByUserid(Long userId);
}
