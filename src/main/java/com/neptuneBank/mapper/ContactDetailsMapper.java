/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See LICENSE file for details.
 *
 *
 */

package com.neptuneBank.mapper;

import com.neptuneBank.DTOs.ContactDetailsDTO.ContactDetailsRequestDTO;
import com.neptuneBank.models.ContactDetails;

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
