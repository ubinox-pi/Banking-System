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

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

    @Query("SELECT u FROM Users u WHERE u.contactDetails.email = ?1")
    Boolean existsByEmail(String email) throws IllegalArgumentException;

    @Query("SELECT u FROM Users u WHERE u.contactDetails.mobileNumber = ?1")
    Boolean exsitsByPhone(String phoneNumber) throws IllegalArgumentException;

    @Query("SELECT u FROM Users u WHERE u.status = ?1")
    List<Users> allUsersByStatus(String status) throws IllegalArgumentException;

    @Query("SELECT U FROM Users U WHERE U.contactDetails.mobileNumber = ?1 OR U.contactDetails.email = ?2 OR U.kycId.aadhaarNumber = ?3 OR U.kycId.panNumber = ?4 OR U.kycId.voterId = ?5 OR U.kycId.passportNumber = ?6 OR U.kycId.drivingLicenseNumber = ?7")
    Boolean userExists(String phoneNumber, String email, String aadhaarNumber, String panNumber, String voterId, String passportNumber, String drivingLicenseNumber) throws IllegalArgumentException;

    @Query("SELECT U FROM Users U")
    List<Users> getAllUsers();

    @Query("SELECT COUNT(u) FROM Users u")
    Integer countUsers();

    @Query("SELECT COUNT(u) FROM Users u WHERE u.contactDetails.email = ?1 OR u.contactDetails.mobileNumber = ?2")
    Boolean checkUserExists(String email, String phoneNumber);
}
