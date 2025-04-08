/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See LICENSE file for details.
 *
 *
 */

package com.neptuneBank.models;

import com.neptuneBank.DTOs.ContactDetailsDTO.ContactDetailsDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ContactDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long contactId;

    //it will take mobile number as string with country code
    @NotBlank(message = "Mobile number is required.")
    @Column(nullable = false, unique = true)
    private String mobileNumber;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Email is required.")
    @Email
    private String email;

    @NotBlank(message = "Communication address is required.")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String communicationAddress;

    @NotBlank(message = "Permanent address is required.")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String permanentAddress;

    @NotBlank(message = "City is required.")
    @Column(nullable = false)
    private String city;

    @NotBlank(message = "State is required.")
    @Column(nullable = false)
    private String state;

    @NotBlank(message = "Pin code is required.")
    @Column(nullable = false)
    private String zip;

    @NotBlank(message = "Landmark is required.")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String landmark;

    @NotBlank(message = "Country is required.")
    @Column(nullable = false)
    private String country;

    @Builder.Default
    private String alternateMobileNumber = null;

    @Builder.Default
    private String alternateEmail = null;

    @Column(nullable = false, updatable = false)
    @PastOrPresent
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @FutureOrPresent
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }


    // DTO implementation

    public void fromDTO(ContactDetailsDTO dto) {
        this.mobileNumber = dto.getMobileNumber();
        this.email = dto.getEmail();
        this.communicationAddress = dto.getCommunicationAddress();
        this.permanentAddress = dto.getPermanentAddress();
        this.city = dto.getCity();
        this.state = dto.getState();
        this.zip = dto.getZip();
        this.landmark = dto.getLandmark();
        this.country = dto.getCountry();
        this.alternateMobileNumber = dto.getAlternateMobileNumber();
        this.alternateEmail = dto.getAlternateEmail();
    }

    public void toDTOId(ContactDetailsDTO dto) {
        this.contactId = dto.getContactId();
    }

    public ContactDetailsDTO toDTO() {
        return ContactDetailsDTO.builder()
                .contactId(this.contactId)
                .mobileNumber(this.mobileNumber)
                .communicationAddress(this.communicationAddress)
                .permanentAddress(this.permanentAddress)
                .city(this.city)
                .state(this.state)
                .zip(this.zip)
                .landmark(this.landmark)
                .country(this.country)
                .alternateMobileNumber(this.alternateMobileNumber)
                .alternateEmail(this.alternateEmail)
                .build();
    }

    public ContactDetailsDTO toAdminDTO() {
        return ContactDetailsDTO.builder()
                .contactId(this.contactId)
                .mobileNumber(this.mobileNumber)
                .communicationAddress(this.communicationAddress)
                .permanentAddress(this.permanentAddress)
                .city(this.city)
                .state(this.state)
                .zip(this.zip)
                .landmark(this.landmark)
                .country(this.country)
                .alternateMobileNumber(this.alternateMobileNumber)
                .alternateEmail(this.alternateEmail)
                .createdAt(this.createdAt)
                .createdAt(this.updatedAt)
                .build();
    }
}
