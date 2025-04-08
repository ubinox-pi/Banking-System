/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See LICENSE file for details.
 *
 *
 */

package com.neptuneBank.DTOs.usersDTO;

import com.neptuneBank.DTOs.ContactDetailsDTO.ContactDetailsDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRegistrationRequest {
    @Valid
    @NotNull(message = "User details are required.")
    private UsersDTO user;

    @Valid
    @NotNull(message = "Contact details are required.")
    private ContactDetailsDTO contactDetails;
}
