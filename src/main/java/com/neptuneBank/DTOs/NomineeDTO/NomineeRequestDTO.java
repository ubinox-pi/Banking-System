/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See LICENSE file for details.
 *
 *
 */

package com.neptuneBank.DTOs.NomineeDTO;

import com.neptuneBank.ENUM.Relationship;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NomineeRequestDTO {
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

    @NotBlank(message = "Aadhar number is required.")
    @Pattern(regexp = "^[2-9]{4}[0-9]{4}[0-9]{4}[0-9]{4}$", message = "Aadhar number is invalid.")
    @Size(min = 12, message = "Aadhar number must be 12 digits.")
    private String nomineeAadhar;

    @NotBlank(message = "Aadhar number is required.")
    @Pattern(regexp = "^[A-Z]{5}[0-9]{4}[A-Z]{1}$", message = "Pan number is invalid.")
    @Size(min = 10, message = "Aadhar number must be 12 digits.")
    private String nomineePan;

    @NotBlank(message = "Nominee address is required.")
    private String nomineeAddress;
}
