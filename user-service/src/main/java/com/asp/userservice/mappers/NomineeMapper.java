/*
 * Copyright (c) 2025 Ayshi Shannidhya Panda. All rights reserved.
 *
 * This source code is confidential and intended solely for internal use.
 * Unauthorized copying, modification, distribution, or disclosure of this
 * file, via any medium, is strictly prohibited.
 *
 * Project: Neptune Bank
 * Author: Ayshi Shannidhya Panda
 * Created on: 20-06-2025
 */
package com.asp.userservice.mappers;

import com.asp.userservice.DTO.NomineeDetailsDTO.NomineeRequestDTO;
import com.asp.userservice.DTO.NomineeDetailsDTO.NomineeResponseDTO;
import com.asp.userservice.models.NomineeDetails;
import org.springframework.stereotype.Component;

@Component
public class NomineeMapper {
    public static NomineeDetails toEntity(NomineeRequestDTO dto) {
        return NomineeDetails.builder()
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

    public static NomineeResponseDTO toResponseDTO(NomineeDetails nominee) {
        if (nominee == null) return null;
        return NomineeResponseDTO.builder()
                .nomineeName(nominee.getNomineeName())
                .nomineeRelationship(nominee.getNomineeRelationship())
                .nomineeDateOfBirth(nominee.getNomineeDateOfBirth())
                .nomineeMobileNumber(nominee.getNomineeMobileNumber())
                .nomineeEmail(nominee.getNomineeEmail())
                .nomineeAadhar(nominee.getNomineeAadhar())
                .nomineePan(nominee.getNomineePan())
                .nomineeAddress(nominee.getNomineeAddress())
                .build();
    }
}
