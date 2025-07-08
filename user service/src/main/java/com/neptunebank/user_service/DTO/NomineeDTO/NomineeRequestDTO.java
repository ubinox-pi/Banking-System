/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project : Neptune
 * Created by: ASUS on 22-05-2025
 * File : NomineeRequestDTO.java
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
package com.neptunebank.user_service.DTO.NomineeDTO;

import com.neptunebank.user_service.ENUMs.Relationship;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NomineeRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Nominee name is required.")
    @Size(min = 8, message = "Nominee name is too short.")
    private String nomineeName;

    @NotNull(message = "Relationship is required.")
    private Relationship nomineeRelationship;

    @NotNull(message = "Date of birth is required.")
    private LocalDate nomineeDateOfBirth;

    @NotBlank(message = "Mobile number is required.")
    private String NomineeMobileNumber;

    @NotBlank(message = "Email is required.")
    private String NomineeEmail;

    @NotBlank(message = "Aadhaar number is required.")
    @Pattern(regexp = "^[2-9]{4}[0-9]{4}[0-9]{4}[0-9]{4}$", message = "Aadhaar number is invalid.")
    @Size(min = 12, message = "Aadhaar number must be 12 digits.")
    private String nomineeAadhaar;

    @NotBlank(message = "Aadhaar number is required.")
    @Pattern(regexp = "^[A-Z]{5}[0-9]{4}[A-Z]{1}$", message = "Pan number is invalid.")
    @Size(min = 10, message = "Aadhaar number must be 12 digits.")
    private String nomineePan;

    @NotBlank(message = "Nominee address is required.")
    private String nomineeAddress;
}
