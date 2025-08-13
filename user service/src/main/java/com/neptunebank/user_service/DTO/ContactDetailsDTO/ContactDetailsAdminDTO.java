package com.neptunebank.user_service.DTO.ContactDetailsDTO;

import com.neptunebank.user_service.ENUMs.Country;
import com.neptunebank.user_service.ENUMs.States;
import lombok.*;

import java.time.LocalDateTime;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.user_service.DTO.ContactDetailsDTO
 * Created by: Ashish Kushwaha on 26-07-2025 22:45
 * File: ContactDetailsAdminDTO
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactDetailsAdminDTO {
    private Long contactId;

    private Long user;

    private String mobileNumber;

    private String email;

    private String communicationAddress;

    private String permanentAddress;

    private String city;

    private States state;

    private String zip;

    private String landmark;

    private Country country;

    private String alternateEmail;

    private String alternateMobileNumber;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
