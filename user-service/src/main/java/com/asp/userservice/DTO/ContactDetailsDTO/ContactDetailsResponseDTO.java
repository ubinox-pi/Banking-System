/*
 * Copyright (c) 2025 Ayshi Shannidhya Panda. All rights reserved.
 *
 * This source code is confidential and intended solely for internal use.
 * Unauthorized copying, modification, distribution, or disclosure of this
 * file, via any medium, is strictly prohibited.
 *
 * Project: Neptune Bank
 * Author: Ayshi Shannidhya Panda
 * Created on: 21-08-2025
 */

package com.asp.userservice.DTO.ContactDetailsDTO;

import lombok.*;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactDetailsResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String mobileNumber;
    private String email;
    private String communicationAddress;
    private String permanentAddress;
    private String city;
    private String state;
    private String zip;
    private String landmark;
    private String country;
    private String alternateMobileNumber;
    private String alternateEmail;
}

