/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project : Neptune
 * Created by: ASUS on 22-05-2025
 * File : UserMapper.java
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
package com.neptunebank.user_service.mappers;


import com.neptunebank.user_service.DTO.userDto.UserAdminDTO;
import com.neptunebank.user_service.DTO.userDto.UsersRequestDTO;
import com.neptunebank.user_service.models.Users;

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
                .category(dto.getCategory())
                .religion(dto.getReligion())
                .contactDetails(ContactDetailsMapper.toEntity(dto.getContactDetails()))
                .nominee(NomineeMapper.toEntity(dto.getNominee()))
                .kycId(KycMapper.toEntity(dto.getKyc()))
                .build();
    }

    public static UserAdminDTO toAdminDTO(Users user) {
        return UserAdminDTO.builder()
                .userId(user.getUserid())
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .gender(user.getGender())
                .fatherName(user.getFatherName())
                .motherName(user.getMotherName())
                .maritalStatus(user.getMaritalStatus())
                .spouseName(user.getSpouseName())
                .occupation(user.getOccupation())
                .salary(user.getSalary())
                .citizen(user.getCitizen())
                .category(user.getCategory())
                .religion(user.getReligion())
                .contactDetails(ContactDetailsMapper.toAdminDto(user.getContactDetails()))
                .accountId(user.getAccountId())
                .nominee(NomineeMapper.toAdminDTO(user.getNominee()))
                .accountInterestRate(user.getAccountInterestRate())
                .kyc(KycMapper.toAdminDto(user.getKycId()))
                .isActive(user.getIsActive())
                .isBlocked(user.getIsBlocked())
                .isDeleted(user.getIsDeleted())
                .status(user.getStatus())
                .description(user.getDescription())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
