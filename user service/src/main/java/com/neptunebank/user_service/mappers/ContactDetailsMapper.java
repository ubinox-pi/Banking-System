/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project : Neptune
 * Created by: ASUS on 22-05-2025
 * File : ContactDetailsMapper.java
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */

package com.neptunebank.user_service.mappers;


import com.neptunebank.user_service.DTO.ContactDetailsDTO.ContactDetailsRequestDTO;
import com.neptunebank.user_service.models.ContactDetails;

public class ContactDetailsMapper {
    public static ContactDetails toEntity(ContactDetailsRequestDTO contactDetails) {
        return ContactDetails.builder()
                .mobileNumber(contactDetails.getMobileNumber())
                .email(contactDetails.getEmail())
                .communicationAddress(contactDetails.getCommunicationAddress())
                .permanentAddress(contactDetails.getPermanentAddress())
                .city(contactDetails.getCity())
                .state(contactDetails.getState())
                .zip(contactDetails.getZip())
                .landmark(contactDetails.getLandmark())
                .country(contactDetails.getCountry())
                .alternateMobileNumber(contactDetails.getAlternateMobileNumber())
                .alternateEmail(contactDetails.getAlternateEmail())
                .build();
    }
}
