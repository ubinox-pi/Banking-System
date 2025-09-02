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
package com.neptunebank.employeeservice.DTOs.employeeDto;


import com.neptunebank.employeeservice.ENUMs.BankRole;
import com.neptunebank.employeeservice.ENUMs.Genders;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeRequestDTO {

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Middle name cannot be blank")
    private String middleName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @NotNull(message = "Date of birth cannot be blank")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email is not valid")
    private String email;

    @NotBlank(message = "Mobile number cannot be blank")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number is not valid")
    private String mobileNumber;

    @NotBlank(message = "Username cannot be blank")
    @Pattern(regexp = "^[aA-zZ0-9_]{5,20}$", message = "Username must be 5-20 characters long and can contain letters, numbers, and underscores only")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one number, and one special character")
    private String password;

    @NotNull(message = "Role cannot be null")
    @Enumerated(EnumType.STRING)
    private BankRole roles;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Gender cannot be null")
    private Genders gender;

}
