/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See LICENSE file for details.
 *
 *
 */

package com.neptuneBank.DTOs;

import com.neptuneBank.DTOs.ContactDetailsDTO.ContactDetailsCreateDTO;
import com.neptuneBank.DTOs.usersDTO.UsersCreateDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRegistrationRequest {
    @Valid
    @NotNull(message = "User details are required.")
    private UsersCreateDTO user;

    @Valid
    @NotNull(message = "Contact details are required.")
    private ContactDetailsCreateDTO contactDetails;
}
