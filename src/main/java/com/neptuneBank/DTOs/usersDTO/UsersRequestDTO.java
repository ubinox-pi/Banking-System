/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See LICENSE file for details.
 *
 *
 */

package com.neptuneBank.DTOs.usersDTO;

import com.neptuneBank.DTOs.ContactDetailsDTO.ContactDetailsRequestDTO;
import com.neptuneBank.DTOs.NomineeDTO.NomineeRequestDTO;
import com.neptuneBank.ENUM.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersRequestDTO {
    @NotBlank(message = "First name is required.")
    @Size(min = 3, message = "First name is too short.")
    private String firstName;

    @NotBlank(message = "First name is required.")
    @Size(min = 3, message = "First name is too short.")
    private String middleName;

    @NotBlank(message = "Lastname is required.")
    @Size(min = 3, message = "Last name is too short.")
    private String lastName;

    @NotBlank(message = "Date of birth is required.")
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

    @NotBlank(message = "Occupation required")
    private Occupation occupation;

    @NotBlank(message = "Occupation required")
    private String salary;

    @NotBlank(message = "Citizen is required.")
    private String citizen;

    @NotNull(message = "Category is required.")
    private Catagory catagory;

    @NotNull(message = "Religion is required.")
    private Religion religion;

    @Valid
    private ContactDetailsRequestDTO contactDetails;

    @Valid
    private NomineeRequestDTO nominee;


}
