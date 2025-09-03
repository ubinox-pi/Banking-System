/*
 * Copyright (c) 2025 Ayshi Shannidhya Panda. All rights reserved.
 *
 * This source code is confidential and intended solely for internal use.
 * Unauthorized copying, modification, distribution, or disclosure of this
 * file, via any medium, is strictly prohibited.
 *
 * Project: Neptune Bank
 * Author: Ayshi Shannidhya Panda
 * Created on: 20-06-2025
 */

package com.asp.userservice.mappers;


import com.asp.userservice.DTO.ContactDetailsDTO.ContactDetailsRequestDTO;
import com.asp.userservice.DTO.ContactDetailsDTO.ContactDetailsResponseDTO;
import com.asp.userservice.models.ContactDetails;
import org.springframework.stereotype.Component;

@Component
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

    public static ContactDetailsResponseDTO toResponseDTO(ContactDetails contactDetails) {
        if (contactDetails == null) return null;
        return ContactDetailsResponseDTO.builder()
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
