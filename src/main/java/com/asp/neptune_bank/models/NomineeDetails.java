package com.asp.neptune_bank.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.asp.neptune_bank.enumeration.Relationship;

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



    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private Relationship relationship;

    @Column(nullable = false)
    private LocalDate dob;

    @Column(nullable = false, length = 15, unique = true)
    private String mob;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false, length = 12, unique = true)
    private String aadhaar;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String address;

    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Column(name = "is_verified")
    private Boolean isVerified = false;


}

