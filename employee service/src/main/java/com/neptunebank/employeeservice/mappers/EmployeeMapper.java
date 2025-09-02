package com.neptunebank.employeeservice.mappers;

import com.neptunebank.employeeservice.DTOs.employeeDto.EmployeeRequestDTO;
import com.neptunebank.employeeservice.models.Employee;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.employeeservice.mappers
 * Created by: Ashish Kushwaha on 28-08-2025 16:13
 * File: EmployeeMapper
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
public class EmployeeMapper {

    public static Employee toEntiry(EmployeeRequestDTO dto) {
        return Employee.builder()
                .firstName(dto.getFirstName())
                .middleName(dto.getMiddleName())
                .lastName(dto.getLastName())
                .dateOfBirth(dto.getDateOfBirth())
                .email(dto.getEmail())
                .mobileNumber(dto.getMobileNumber())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .roles(dto.getRoles())
                .gender(dto.getGender())
                .build();
    }
}
