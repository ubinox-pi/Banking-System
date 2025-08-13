/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project : Neptune
 * Created by: ASUS on 22-05-2025
 * File : ContactDetails.java
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */

package com.neptunebank.user_service.models;

import com.neptunebank.user_service.ENUMs.Country;
import com.neptunebank.user_service.ENUMs.States;
import jakarta.persistence.*;
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

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "userId", referencedColumnName = "userId", unique = true, updatable = false)
    private Users user;
    
    @Column(nullable = false, unique = true)
    private String mobileNumber;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String communicationAddress;

    @NotBlank(message = "Permanent address is required.")
    private String permanentAddress;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private States state;

    @Column(nullable = false)
    private String zip;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String landmark;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Country country;

    @Builder.Default
    private String alternateMobileNumber = null;

    @Builder.Default
    private String alternateEmail = null;

    @Column(nullable = false, updatable = false)
    @PastOrPresent
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @PastOrPresent
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        if (this.mobileNumber != null)
            if (!this.mobileNumber.startsWith("+91"))
                this.mobileNumber = "+91" + this.mobileNumber.trim();


        if (this.email != null)
            if (!this.email.equals(this.email.toUpperCase()))
                this.email = this.email.toUpperCase().trim();

        if (this.communicationAddress != null)
            if (!this.communicationAddress.equals(this.communicationAddress.toUpperCase()))
                this.communicationAddress = this.communicationAddress.toUpperCase().trim();

        if (this.permanentAddress != null)
            if (!this.permanentAddress.equals(this.permanentAddress.toUpperCase()))
                this.permanentAddress = this.permanentAddress.toUpperCase().trim();

        if (this.city != null)
            if (!this.city.equals(this.city.toUpperCase()))
                this.city = this.city.toUpperCase().trim();

        if (this.landmark != null)
            if (!this.landmark.equals(this.landmark.toUpperCase()))
                this.landmark = this.landmark.toUpperCase().trim();

        if (this.alternateMobileNumber != null)
            if (!this.alternateMobileNumber.startsWith("+91"))
                this.alternateMobileNumber = "+91" + this.alternateMobileNumber.trim();

        if (this.alternateEmail != null)
            if (!this.alternateEmail.equals(this.alternateEmail.toUpperCase()))
                this.alternateEmail = this.alternateEmail.toUpperCase().trim();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();

        if (this.mobileNumber != null)
            if (!this.mobileNumber.startsWith("+91"))
                this.mobileNumber = "+91" + this.mobileNumber.trim();


        if (this.email != null)
            if (!this.email.equals(this.email.toUpperCase()))
                this.email = this.email.toUpperCase().trim();

        if (this.communicationAddress != null)
            if (!this.communicationAddress.equals(this.communicationAddress.toUpperCase()))
                this.communicationAddress = this.communicationAddress.toUpperCase().trim();

        if (this.permanentAddress != null)
            if (!this.permanentAddress.equals(this.permanentAddress.toUpperCase()))
                this.permanentAddress = this.permanentAddress.toUpperCase().trim();

        if (this.city != null)
            if (!this.city.equals(this.city.toUpperCase()))
                this.city = this.city.toUpperCase().trim();

        if (this.landmark != null)
            if (!this.landmark.equals(this.landmark.toUpperCase()))
                this.landmark = this.landmark.toUpperCase().trim();

        if (this.alternateMobileNumber != null)
            if (!this.alternateMobileNumber.startsWith("+91"))
                this.alternateMobileNumber = "+91" + this.alternateMobileNumber.trim();

        if (this.alternateEmail != null)
            if (!this.alternateEmail.equals(this.alternateEmail.toUpperCase()))
                this.alternateEmail = this.alternateEmail.toUpperCase().trim();
    }


}
