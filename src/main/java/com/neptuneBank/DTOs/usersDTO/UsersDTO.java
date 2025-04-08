/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See LICENSE file for details.
 *
 *
 */

package com.neptuneBank.DTOs.usersDTO;

import com.neptuneBank.models.ENUM.Genders;
import com.neptuneBank.models.ENUM.MaritalStatus;
import com.neptuneBank.models.ENUM.Occupation;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersDTO {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Genders gender;
    private String fatherName;
    private String motherName;
    private MaritalStatus maritalStatus;
    private String spouseName;
    @Valid
    private Coo contactDetails;
    private Occupation occupation;
    private String aadhar;
    private String pan;
    private Double accountInterestRate;
}
