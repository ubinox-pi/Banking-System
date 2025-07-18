package com.asp.employeeservice.dto;

/*
 * Copyright (c) 2025 Ayshi Shannidhya Panda. All rights reserved.
 *
 * This source code is confidential and intended solely for internal use.
 * Unauthorized copying, modification, distribution, or disclosure of this
 * file, via any medium, is strictly prohibited.
 *
 * Project: Neptune Bank
 * Author: Ayshi Shannidhya Panda
 * Created on: 22-06-2025
 */


import com.asp.employeeservice.enumeration.EmployeeRole;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Employee name is required.")
    @Size(min = 3, message = "Employee name must be at least 3 characters long.")
    private String name;

    @NotBlank(message = "Employee email is required.")
    @Email(message = "Invalid email format.")
    private String email;

    @NotBlank(message = "Phone number is required.")
    @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 characters.")
    private String phone;

    @NotNull(message = "Employee role must be specified.")
    private EmployeeRole role;

//    @NotNull(message = "Branch ID is required.")
//    private Long branchId;

//    @Valid
//    private AccountDetailsDTO accountDetails;
}


