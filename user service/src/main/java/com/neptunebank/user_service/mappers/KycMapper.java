package com.neptunebank.user_service.mappers;

import com.neptunebank.user_service.DTO.kycDTO.KycAdminDTO;
import com.neptunebank.user_service.DTO.kycDTO.KycRequestDTO;
import com.neptunebank.user_service.models.Kyc;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.user_service.mappers
 * Created by: Ashish Kushwaha on 25-05-2025 14:59
 * File: KycMapper
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
public class KycMapper {

    public static Kyc toEntity(KycRequestDTO dto) {
        return Kyc.builder()
                .aadhaarNumber(dto.getAadhaarNumber())
                .panNumber(dto.getPanNumber())
                .voterId(dto.getVoterId())
                .passportNumber(dto.getPassportNumber())
                .drivingLicenseNumber(dto.getDrivingLicenseNumber())
                .build();
    }

    public static KycAdminDTO toAdminDto(Kyc kyc) {
        return KycAdminDTO.builder()
                .kycId(kyc.getKycId())
                .user(kyc.getUser().getUserid())
                .aadhaarNumber(kyc.getAadhaarNumber())
                .aadhaarImage(kyc.getAadhaarImage())
                .aadhaarVerified(kyc.getAadhaarVerified())
                .panNumber(kyc.getPanNumber())
                .panImage(kyc.getPanImage())
                .panVerified(kyc.getPanVerified())
                .userPhoto(kyc.getUserPhoto())
                .userPhotoVerified(kyc.getUserPhotoVerified())
                .userSignature(kyc.getUserSignature())
                .userSignatureVerified(kyc.getUserSignatureVerified())
                .voterId(kyc.getVoterId())
                .voterIdImage(kyc.getVoterIdImage())
                .voterIdVerified(kyc.getVoterIdVerified())
                .passportNumber(kyc.getPassportNumber())
                .passportImage(kyc.getPassportImage())
                .passportVerified(kyc.getPassportVerified())
                .drivingLicenseNumber(kyc.getDrivingLicenseNumber())
                .drivingLicenseImage(kyc.getDrivingLicenseImage())
                .drivingLicenseVerified(kyc.getDrivingLicenseVerified())
                .status(kyc.getStatus())
                .rejectionReason(kyc.getRejectionReason())
                .createdAt(kyc.getCreatedAt())
                .updatedAt(kyc.getUpdatedAt())
                .build();
    }
}
