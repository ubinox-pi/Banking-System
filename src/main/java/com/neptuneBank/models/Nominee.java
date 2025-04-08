/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See LICENSE file for details.
 *
 *
 */

package com.neptuneBank.models;

import com.neptuneBank.DTOs.NomineeDTO.NomineeDTO;
import com.neptuneBank.models.ENUM.Relationship;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Nominee {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nomineeId;

    @Valid
    @NotNull
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(nullable = false, name = "accountId", referencedColumnName = "accountId", unique = true, updatable = false)
    private Account account;

    @NotBlank(message = "Nominee name is required.")
    @Size(min = 8, message = "Nominee name is too short.")
    @Column(nullable = false)
    private String nomineeName;

    @NotNull(message = "Relationship is required.")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Relationship relationship;

    @NotNull(message = "Date of birth is required.")
    @Column(nullable = false)
    @Past
    private LocalDate dateOfBirth;

    @NotBlank(message = "Mobile number is required.")
    @Column(nullable = false, unique = true)
    private String mobileNumber;

    @NotBlank(message = "Email is required.")
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Aadhar number is required.")
    @Pattern(regexp = "^[2-9]{4}[0-9]{4}[0-9]{4}[0-9]{4}$", message = "Aadhar number is invalid.")
    @Size(min = 12, message = "Aadhar number must be 12 digits.")
    @Column(nullable = false, unique = true)
    private String nomineeAadhar;

    @NotBlank(message = "Aadhar number is required.")
    @Pattern(regexp = "^[A-Z]{5}[0-9]{4}[A-Z]{1}$", message = "Pan number is invalid.")
    @Size(min = 10, message = "Aadhar number must be 12 digits.")
    @Column(nullable = false, unique = true)
    private String nomineePan;


    @NotBlank(message = "Nominee address is required.")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String fullAddress;

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
    public void fromDTO(NomineeDTO dto) {
        this.account = dto.getAccount();
        this.nomineeName = dto.getNomineeName();
        this.relationship = dto.getRelationship();
        this.dateOfBirth = dto.getDateOfBirth();
        this.mobileNumber = dto.getMobileNumber();
        this.email = dto.getEmail();
        this.nomineeAadhar = dto.getNomineeAadhar();
        this.nomineePan = dto.getNomineePan();
        this.fullAddress = dto.getFullAddress();
    }

    public NomineeDTO toDTO() {
        return NomineeDTO.builder()
                .nomineeId(this.nomineeId)
                .account(this.account)
                .nomineeName(this.nomineeName)
                .relationship(this.relationship)
                .dateOfBirth(this.dateOfBirth)
                .mobileNumber(this.mobileNumber)
                .email(this.email)
                .nomineeAadhar(this.nomineeAadhar)
                .nomineePan(this.nomineePan)
                .fullAddress(this.fullAddress)
                .build();
    }

    public NomineeDTO toAdminDTO() {
        return NomineeDTO.builder()
                .nomineeId(this.nomineeId)
                .account(this.account)
                .nomineeName(this.nomineeName)
                .relationship(this.relationship)
                .dateOfBirth(this.dateOfBirth)
                .mobileNumber(this.mobileNumber)
                .email(this.email)
                .nomineeAadhar(this.nomineeAadhar)
                .nomineePan(this.nomineePan)
                .fullAddress(this.fullAddress)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }
}
