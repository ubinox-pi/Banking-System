/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project : Neptune
 * Created by: ASUS on 22-05-2025
 * File : UserRequestDTO.java
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
package com.neptunebank.user_service.DTO.userDto;


import com.neptunebank.user_service.DTO.ContactDetailsDTO.ContactDetailsRequestDTO;
import com.neptunebank.user_service.DTO.NomineeDTO.NomineeRequestDTO;
import com.neptunebank.user_service.DTO.kycDTO.KycRequestDTO;
import com.neptunebank.user_service.ENUMs.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "First name is required.")
    @Size(min = 3, message = "First name is too short.")
    private String firstName;

    @NotBlank(message = "First name is required.")
    @Size(min = 3, message = "First name is too short.")
    private String middleName;

    @NotBlank(message = "Lastname is required.")
    @Size(min = 3, message = "Last name is too short.")
    private String lastName;

    @NotNull(message = "Date of birth is required.")
    @Past
    private LocalDate dateOfBirth;

    @NotNull(message = "Gender is required.")
    private Genders gender;

    @NotBlank(message = "Father name is required.")
    @Size(min = 8, message = "Father name is too short.")
    private String fatherName;

    @NotBlank(message = "Mother name is required.")
    @Size(min = 8, message = "Mother name is too short.")
    private String motherName;

    @NotNull(message = "Marital status is required.")
    private MaritalStatus maritalStatus;

    @Size(min = 5, message = "Spouse name is too short.")
    private String spouseName;

    @NotNull(message = "Occupation required")
    private Occupation occupation;

    @NotBlank(message = "Occupation required")
    private String salary;

    @NotBlank(message = "Citizen is required.")
    private String citizen;

    @NotNull(message = "Category is required.")
    private Category category;

    @NotNull(message = "Religion is required.")
    private Religion religion;

    @Valid
    private ContactDetailsRequestDTO contactDetails;

    @Valid
    private NomineeRequestDTO nominee;

    @Valid
    private KycRequestDTO kyc;


}
