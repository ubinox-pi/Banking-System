/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See LICENSE file for details.
 *
 *
 */

package com.neptuneBank.mapper;

import com.neptuneBank.DTOs.NomineeDTO.NomineeRequestDTO;
import com.neptuneBank.models.Nominee;

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
