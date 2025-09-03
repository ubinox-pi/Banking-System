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

import com.asp.userservice.DTO.KycDTO.KycRequestDTO;
import com.asp.userservice.DTO.KycDTO.KycResponseDTO;
import com.asp.userservice.models.Kyc;
import org.springframework.stereotype.Component;

@Component
public class KycMapper {

    public static Kyc toEntity(KycRequestDTO dto) {
        return Kyc.builder()
                .aadharNumber(dto.getAadharNumber())
                .panNumber(dto.getPanNumber())
                .voterId(dto.getVoterId())
                .passportNumber(dto.getPassportNumber())
                .drivingLicenseNumber(dto.getDrivingLicenseNumber())
                .build();
    }

    public static KycResponseDTO toResponseDTO(Kyc kyc) {
        if (kyc == null) return null;
        KycResponseDTO dto = new KycResponseDTO();
        dto.setAadharNumber(kyc.getAadharNumber());
        dto.setAadharImage(kyc.getAadharImage());
        dto.setAadharVerified(kyc.getAadharVerified());
        dto.setPanNumber(kyc.getPanNumber());
        dto.setPanImage(kyc.getPanImage());
        dto.setPanVerified(kyc.getPanVerified());
        dto.setVoterId(kyc.getVoterId());
        dto.setVoterIdImage(kyc.getVoterIdImage());
        dto.setVoterIdVerified(kyc.getVoterIdVerified());
        dto.setPassportNumber(kyc.getPassportNumber());
        dto.setPassportImage(kyc.getPassportImage());
        dto.setPassportVerified(kyc.getPassportVerified());
        dto.setDrivingLicenseNumber(kyc.getDrivingLicenseNumber());
        dto.setDrivingLicenseImage(kyc.getDrivingLicenseImage());
        dto.setDrivingLicenseVerified(kyc.getDrivingLicenseVerified());
        dto.setVerifiedByEmployeeId(kyc.getVerifiedByEmployeeId());
        dto.setRejectionReason(kyc.getRejectionReason());
        return dto;
    }
}
