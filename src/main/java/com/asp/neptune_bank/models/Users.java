package com.asp.neptune_bank.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(updatable = false)
    private Long id;

    @NotBlank(message = "First Name cannot be blank")
    @Size(min = 3)
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Last Name cannot be blank")
    private String lastName;

    @NotNull
    private LocalDate dateOfBirth;

    @NotBlank(message = "Gender cannot be blank")
    @Size(max = 10)
    @Column(nullable = false)
    private String gender;

    @NotBlank(message = "Father Name cannot be blank")
    @Size(max = 50)
    @Column(nullable = false)
    private String fatherName;

    @NotBlank(message = "Mother Name cannot be blank")
    @Size(max = 50)
    @Column(nullable = false)
    private String motherName;

    @NotBlank(message = "Martial Status cannot be blank")
    @Column(nullable = false)
    private String maritalStatus;


    private String spouseName;

    @NotBlank(message = "Occupation cannot be blank")
    @Column(nullable = false)
    private String occupation;

    @NotBlank(message = "Aadhar Number cannot be blank")
    @Column(nullable = false)
    private String aadhar;

    @NotBlank(message = "PAN cannot be blank")
    @Column(nullable = false)
    private String pan;

    @NotNull
    @Builder.Default
    private Double accountInterestRate = 0.0d;

    @NotNull
    @Builder.Default
    private Boolean isKycCompleted = null;

    @NotNull
    @Builder.Default
    private Boolean isVerified = true;

    @NotNull
    @Builder.Default
    private Boolean isActive = null;

    @NotNull
    @Builder.Default
    private Boolean isBlocked = null;

    @NotNull
    @Builder.Default
    private Boolean isDeleted = null;

    @Version
    private Integer version;

    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contactId", referencedColumnName = "contactId")
    private ContactDetails contactDetails;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "accountId", referencedColumnName = "accountId")
    private AccountDetails accountDetails;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "nomineeId", referencedColumnName = "nomineeId")
    private NomineeDetails nomineeDetails;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }


}

