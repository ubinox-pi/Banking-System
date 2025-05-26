package com.neptunebank.user_service.repositories;

import com.neptunebank.user_service.exception.usersException.entity.OtpRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.user_service.repositories
 * Created by: Ashish Kushwaha on 25-05-2025 23:57
 * File: OtoRepository
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
public interface OtpRepository extends JpaRepository<OtpRecord, Long> {

    @Query("SELECT COUNT(o) > 0 FROM OtpRecord o WHERE o.otp = ?1 AND o.isUsed = false AND o.isExpired = false")
    boolean checkOtp(String otp);

    @Query("SELECT COUNT(o) > 0 FROM OtpRecord o WHERE o.otp = ?1 AND o.emailOrPhone = ?2 AND o.isUsed = false AND o.isExpired = false")
    boolean checkOtp(String otp, String emailOrPhone);

    @Modifying
    @Query("UPDATE OtpRecord o SET o.isExpired = true WHERE o.otp = ?1 AND o.emailOrPhone = ?2 AND o.isExpired = false")
    void expireOtp(String otp, String emailOrPhone);
}
