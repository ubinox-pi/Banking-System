/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See LICENSE file for details.
 *
 *
 */

package com.neptuneBank.DTOs.usersDTO;

import com.neptuneBank.DTOs.ContactDetailsDTO.ContactDetailsDTO;
import com.neptuneBank.models.Account;
import com.neptuneBank.models.ENUM.Genders;
import com.neptuneBank.models.ENUM.MaritalStatus;
import com.neptuneBank.models.ENUM.Occupation;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersDTO {
    private Long userid;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Genders gender;
    private String fatherName;
    private String motherName;
    private MaritalStatus maritalStatus;
    private String spouseName;
    @Valid
    private ContactDetailsDTO contactDetails;
    private Occupation occupation;
    private String aadhar;
    private String pan;
    private Account account;
    private Double accountInterestRate;
    private Boolean isKycCompleted = null;
    private Boolean isVerified = false;
    private Boolean isActive = null;
    private Boolean isBlocked = null;
    private Boolean isDeleted = null;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;


}
