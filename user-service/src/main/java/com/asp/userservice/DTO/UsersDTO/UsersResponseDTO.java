/*
 * Copyright (c) 2025 Ayshi Shannidhya Panda. All rights reserved.
 *
 * This source code is confidential and intended solely for internal use.
 * Unauthorized copying, modification, distribution, or disclosure of this
 * file, via any medium, is strictly prohibited.
 *
 * Project: Neptune Bank
 * Author: Ayshi Shannidhya Panda
 * Created on: 21-08-2025
 */
package com.asp.userservice.DTO.UsersDTO;

import com.asp.userservice.DTO.ContactDetailsDTO.ContactDetailsResponseDTO;
import com.asp.userservice.DTO.KycDTO.KycResponseDTO;
import com.asp.userservice.DTO.NomineeDetailsDTO.NomineeResponseDTO;
import com.asp.userservice.enumeration.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Genders gender;
    private String fatherName;
    private String motherName;
    private MaritalStatus maritalStatus;
    private String spouseName;
    private Occupation occupation;
    private String salary;
    private String citizen;
    private Category category;
    private Religion religion;
    private ContactDetailsResponseDTO contactDetails;
    private NomineeResponseDTO nominee;
    private KycResponseDTO kyc;
}

