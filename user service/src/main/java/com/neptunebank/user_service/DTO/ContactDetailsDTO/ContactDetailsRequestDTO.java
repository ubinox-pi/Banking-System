/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project : Neptune
 * Created by: ASUS on 22-05-2025
 * File : ContactDetailsRequestDTO.java
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
package com.neptunebank.user_service.DTO.ContactDetailsDTO;

import com.neptunebank.user_service.ENUMs.Country;
import com.neptunebank.user_service.ENUMs.States;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactDetailsRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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

    @NotNull(message = "State is required.")
    private States state;

    @NotBlank(message = "Pin code is required.")
    private String zip;

    @NotBlank(message = "Landmark is required.")
    private String landmark;

    @NotNull(message = "Country is required.")
    private Country country;
    @Builder.Default
    private String alternateMobileNumber = null;
    @Builder.Default
    private String alternateEmail = null;
}
