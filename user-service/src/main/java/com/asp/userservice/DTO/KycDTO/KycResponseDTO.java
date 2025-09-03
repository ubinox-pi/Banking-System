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

package com.asp.userservice.DTO.KycDTO;


import com.asp.userservice.models.Users;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KycResponseDTO {
    private Users user;
    private String aadharNumber;
    private byte[] aadharImage;
    private Boolean aadharVerified;
    private String panNumber;
    private byte[] panImage;
    private Boolean panVerified;
    private String voterId;
    private byte[] voterIdImage;
    private Boolean voterIdVerified;
    private String passportNumber;
    private byte[] passportImage;
    private Boolean passportVerified;
    private String drivingLicenseNumber;
    private byte[] drivingLicenseImage;
    private Boolean drivingLicenseVerified;
    private Long verifiedByEmployeeId;
    private String rejectionReason;

}
