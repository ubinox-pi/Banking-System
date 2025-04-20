/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See LICENSE file for details.
 *
 *
 */

package com.neptuneBank.mapper;

import com.neptuneBank.DTOs.usersDTO.UsersRequestDTO;
import com.neptuneBank.models.Users;

public class UsersMapper {
    public static Users toEntity(UsersRequestDTO dto) {
        return Users.builder()
                .firstName(dto.getFirstName())
                .middleName(dto.getMiddleName())
                .lastName(dto.getLastName())
                .dateOfBirth(dto.getDateOfBirth())
                .gender(dto.getGender())
                .fatherName(dto.getFatherName())
                .motherName(dto.getMotherName())
                .maritalStatus(dto.getMaritalStatus())
                .spouseName(dto.getSpouseName())
                .occupation(dto.getOccupation())
                .salary(dto.getSalary())
                .citizen(dto.getCitizen())
                .category(dto.getCatagory())
                .religion(dto.getReligion())
                .contactDetails(ContactDetailsMapper.toEntity(dto.getContactDetails()))
                .nominee(NomineeMapper.toEntity(dto.getNominee()))
                .build();
    }
}
