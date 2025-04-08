/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See LICENSE file for details.
 *
 *
 */

package com.neptuneBank.DTOs.ContactDetailsDTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactDetailsDTO {
    private Long contactId;
    private String mobileNumber;
    private String email;
    private String communicationAddress;
    private String permanentAddress;
    private String city;
    private String state;
    private String zip;
    private String landmark;
    private String country;
    @Builder.Default
    private String alternateMobileNumber = null;
    @Builder.Default
    private String alternateEmail = null;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
