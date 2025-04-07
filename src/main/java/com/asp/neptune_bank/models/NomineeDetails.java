package com.asp.neptune_bank.models;

import com.asp.neptune_bank.enumeration.Relationship;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NomineeDetails {

    @Id
    @Setter(AccessLevel.NONE)
    @Column(nullable = false, length = 100)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nomineeId;


    @NotBlank(message = "Nominee Name cannot be blank")
    @Column(nullable = false, length = 100)
    private String name;

    @NotNull(message = "Nominee Realtioship cannot be blank")
    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private Relationship relationship;

    @NotNull(message = "Nominee DOB cannot be blank")
    @Column(nullable = false)
    private LocalDate dob;

    @NotBlank(message = "Nominee mobile cannot be blank")
    @Column(nullable = false, length = 15, unique = true)
    private String mob;

    @NotBlank(message = "Nominee Email cannot be blank")
    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @NotBlank(message = "Nominee Aadhar cannot be blank")
    @Column(nullable = false, length = 12, unique = true)
    private String aadhaar;

    @NotBlank(message = "Nominee Address cannot be blank")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String address;

    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Column(name = "is_verified")
    private Boolean isVerified = false;

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

