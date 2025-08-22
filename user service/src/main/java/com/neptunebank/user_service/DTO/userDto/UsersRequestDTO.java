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


import com.fasterxml.jackson.annotation.JsonFormat;
import com.neptunebank.user_service.DTO.ContactDetailsDTO.ContactDetailsRequestDTO;
import com.neptunebank.user_service.DTO.NomineeDTO.NomineeRequestDTO;
import com.neptunebank.user_service.DTO.kycDTO.KycRequestDTO;
import com.neptunebank.user_service.ENUMs.*;
import com.neptunebank.user_service.validation.MinAge;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
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

    @Schema(description = "User first name", example = "ASHISH")
    @NotBlank(message = "First name is required.")
    @Pattern(regexp = "^(?=(?:.*[A-Za-z]){3,})[A-Za-z]{3,64}$",
            message = "First name must be 3-64 letters only.")
    private String firstName;

    @Schema(description = "User middle name", example = "KUMAR")
    @Pattern(regexp = "^$|^(?=(?:.*[A-Za-z]){3,})[A-Za-z]{3,64}$",
            message = "Middle name must be 3-64 letters only if provided.")
    private String middleName;

    @Schema(description = "User last name", example = "KUSHWAHA")
    @NotBlank(message = "Last name is required.")
    @Pattern(regexp = "^(?=(?:.*[A-Za-z]){3,})[A-Za-z]{3,64}$",
            message = "Last name must be 3-64 letters only.")
    private String lastName;

    @Schema(description = "User date of birth", example = "1990-01-01")
    @NotNull(message = "Date of birth is required.")
    @Past
    @MinAge(value = 18, message = "User must be at least 18 years old.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private LocalDate dateOfBirth;

    @Schema(description = "User gender", examples = {"MALE", "FEMALE", "OTHER"})
    @NotNull(message = "Gender is required.")
    private Genders gender;

    @Schema(description = "User father name", example = "ASHISH KUMAR")
    @NotBlank(message = "Father name is required.")
    @Size(min = 8, message = "Father name is too short.")
    private String fatherName;

    @Schema(description = "User mother name", example = "ASHISH KUMAR")
    @NotBlank(message = "Mother name is required.")
    @Size(min = 8, message = "Mother name is too short.")
    private String motherName;

    @Schema(description = "User marital status", examples = {"MARRIED", "UNMARRIED", "WIDOWED", "DIVORCED", "WIDOWER"})
    @NotNull(message = "Marital status is required.")
    private MaritalStatus maritalStatus;

    @Schema(description = "User spouse name", example = "")
    @Pattern(regexp = "^$|^(?=(?:.*[A-Za-z]){5,})[A-Za-z]{3,64}$",
            message = "Spouse name must have at least 5 letters and be 3-64 characters if provided.")
    private String spouseName;

    @NotNull(message = "Occupation required")
    private Occupation occupation;

    @NotBlank(message = "Occupation required")
    private String salary;

    @NotNull(message = "Citizen is required.")
    private Citizen citizen;

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
