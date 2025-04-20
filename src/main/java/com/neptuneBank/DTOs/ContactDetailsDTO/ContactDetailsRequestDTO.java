/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See LICENSE file for details.
 *
 *
 */

package com.neptuneBank.DTOs.ContactDetailsDTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactDetailsRequestDTO {
    @NotBlank(message = "Mobile number is required.")
    private String mobileNumber;

    @NotBlank(message = "Email is required.")
    @Email
    private String email;

    @NotBlank(message = "Communication address is required.")
    private String communicationAddress;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String permanentAddress;

    @NotBlank(message = "City is required.")
    @Size(min = 3, message = "City name is too short.")
    private String city;

    @NotBlank(message = "State is required.")
    private String state;

    @NotBlank(message = "Pin code is required.")
    private String zip;

    @NotBlank(message = "Landmark is required.")
    private String landmark;

    @NotBlank(message = "Country is required.")
    private String country;
    @Builder.Default
    private String alternateMobileNumber = null;
    @Builder.Default
    private String alternateEmail = null;
}
