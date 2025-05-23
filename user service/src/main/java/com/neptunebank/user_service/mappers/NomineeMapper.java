/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project : Neptune
 * Created by: ASUS on 22-05-2025
 * File : NomineeMapper.java
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


import com.neptunebank.user_service.DTO.NomineeDTO.NomineeRequestDTO;
import com.neptunebank.user_service.models.Nominee;

public class NomineeMapper {
    public static Nominee toEntity(NomineeRequestDTO dto) {
        return Nominee.builder()
                .nomineeName(dto.getNomineeName())
                .nomineeRelationship(dto.getNomineeRelationship())
                .nomineeDateOfBirth(dto.getNomineeDateOfBirth())
                .nomineeMobileNumber(dto.getNomineeMobileNumber())
                .NomineeEmail(dto.getNomineeEmail())
                .nomineeAadhar(dto.getNomineeAadhar())
                .nomineePan(dto.getNomineePan())
                .nomineeAddress(dto.getNomineeAddress())
                .build();
    }
}
