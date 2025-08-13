package com.neptunebank.user_service.DTO.kycDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.user_service.DTO
 * Created by: Ashish Kushwaha on 25-05-2025 14:42
 * File: KycRequestDTO
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KycRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Aadhaar number is required.")
    @Pattern(regexp = "^[0-9]{12}$", message = "Aadhaar number must be a 12-digit number.")
    @Size(min = 12, max = 12, message = "Aadhaar number must be 12 digits.")
    private String aadhaarNumber;

    @NotBlank(message = "PAN number is required.")
    @Pattern(regexp = "^[A-Z]{5}[0-9]{4}[A-Z]{1}$", message = "PAN number must be a 12-digit number.")
    @Size(min = 10, max = 10, message = "PAN number must be 10 characters.")
    private String panNumber;

    @Size(min = 10, max = 10, message = "Voter ID number must be 10 characters.")
    @Pattern(regexp = "^$[A-Z]{3}[0-9]{7}$", message = "Voter ID number must start with 3 letters followed by 7 numbers")
    private String voterId;
    private String passportNumber;
    private String drivingLicenseNumber;

}
