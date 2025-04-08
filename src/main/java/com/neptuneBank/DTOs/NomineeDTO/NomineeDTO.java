/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See LICENSE file for details.
 *
 *
 */

package com.neptuneBank.DTOs.NomineeDTO;

import com.neptuneBank.models.Account;
import com.neptuneBank.models.ENUM.Relationship;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NomineeDTO {
    private Long nomineeId;
    private Account account;
    private String nomineeName;
    private Relationship relationship;
    private LocalDate dateOfBirth;
    private String mobileNumber;
    private String email;
    private String nomineeAadhar;
    private String nomineePan;
    private String fullAddress;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
