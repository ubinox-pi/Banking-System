/*
 *
 *  * Copyright (c) 2025 Ramjee Prasad
 *  * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 *  * See LICENSE file for details.
 *
 *
 */


package com.neptuneBank.models;

import com.neptuneBank.DTOs.usersDTO.UsersDTO;
import com.neptuneBank.models.ENUM.Genders;
import com.neptuneBank.models.ENUM.MaritalStatus;
import com.neptuneBank.models.ENUM.Occupation;
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
public class Users {

    //unique id to be added
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long userid;

    @NotBlank(message = "First name is required.")
    @Size(min = 3, message = "First name is too short.")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Lastname is required.")
    @Size(min = 3, message = "Last name is too short.")
    @Column(nullable = false)
    private String lastName;

    @NotBlank(message = "Date of birth is required.")
    @Column(nullable = false)
    @Past
    private LocalDate dateOfBirth;

    @NotNull(message = "Gender is required.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genders gender;

    @NotBlank(message = "Father name is required.")
    @Size(min = 8, message = "Father name is too short.")
    @Column(nullable = false)
    private String fatherName;

    @NotBlank(message = "Mother name is required.")
    @Size(min = 8, message = "Mother name is too short.")
    @Column(nullable = false)
    private String motherName;

    @NotNull(message = "Marital status is required.")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    @Builder.Default
    private String spouseName = null;

    @Valid
    @NotNull(message = "Contact details is not mapped correctly.")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(nullable = false, name = "contactId", referencedColumnName = "contactId", unique = true)
    private ContactDetails contactDetails;

    @NotBlank(message = "Occupation required")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Occupation occupation;

    @NotBlank(message = "Aadhar is required.")
    @Pattern(regexp = "^[2-9]{4}[0-9]{4}[0-9]{4}[0-9]{4}$", message = "Aadhar number is invalid.")
    @Size(min = 16, message = "Aadhar number is too short.")
    @Column(nullable = false, unique = true)
    private String aadhar;

    @NotBlank(message = "Pan is required")
    @Pattern(regexp = "^[A-Z]{5}[0-9]{4}[A-Z]{1}$", message = "Pan number is invalid.")
    @Size(min = 10, message = "Pan number is too short.")
    @Column(nullable = false, updatable = false)
    private String pan;

    @Valid
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "accountId", referencedColumnName = "accountId", unique = true)
    @Builder.Default
    private Account account = null;

    @Column(nullable = false)
    @Builder.Default
    private Double accountInterestRate = 0.0d;

    //This will a class in later
    @Builder.Default
    private Boolean isKycCompleted = null;

    //This will a class in later
    @Builder.Default
    private Boolean isVerified = false;

    @Builder.Default
    private Boolean isActive = null;

    @Builder.Default
    private Boolean isBlocked = null;

    @Builder.Default
    private Boolean isDeleted = null;

    @Column(nullable = false, updatable = false)
    @PastOrPresent
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @FutureOrPresent
    private LocalDateTime updatedAt;

    public void fromDTO(UsersDTO dto) {
        this.firstName = dto.getFirstName();
        this.lastName = dto.getLastName();
        this.dateOfBirth = dto.getDateOfBirth();
        this.gender = dto.getGender();
        this.fatherName = dto.getFatherName();
        this.motherName = dto.getMotherName();
        this.maritalStatus = dto.getMaritalStatus();
        this.spouseName = dto.getSpouseName();
        this.contactDetails = new ContactDetails();
        this.contactDetails.toDTOId(dto.getContactDetails());
        this.occupation = dto.getOccupation();
        this.aadhar = dto.getAadhar();
        this.pan = dto.getPan();
        this.accountInterestRate = dto.getAccountInterestRate();
    }

    public UsersDTO toDTO() {
        return UsersDTO.builder()
                .userid(this.getUserid())
                .firstName(this.firstName)
                .lastName(this.lastName)
                .dateOfBirth(this.dateOfBirth)
                .gender(this.gender)
                .fatherName(this.fatherName)
                .motherName(this.motherName)
                .maritalStatus(this.maritalStatus)
                .spouseName(this.spouseName)
                .contactDetails(this.contactDetails.toDTO())
                .occupation(this.occupation)
                .aadhar(this.aadhar)
                .pan(this.pan)
                .account(this.account)
                .accountInterestRate(this.accountInterestRate)
                .build();
    }

    public UsersDTO toAdminDTO() {
        return UsersDTO.builder()
                .userid(this.userid)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .dateOfBirth(this.dateOfBirth)
                .gender(this.gender)
                .fatherName(this.fatherName)
                .motherName(this.motherName)
                .maritalStatus(this.maritalStatus)
                .spouseName(this.spouseName)
                .contactDetails(this.contactDetails.toDTO())
                .occupation(this.occupation)
                .aadhar(this.aadhar)
                .pan(this.pan)
                .account(this.account)
                .accountInterestRate(this.accountInterestRate)
                .isKycCompleted(this.isKycCompleted)
                .isVerified(this.isVerified)
                .isActive(this.isActive)
                .isBlocked(this.isBlocked)
                .isDeleted(this.isDeleted)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }


    //DTO implementation

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }


}
