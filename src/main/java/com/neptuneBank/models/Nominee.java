/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See LICENSE file for details.
 *
 *
 */

package com.neptuneBank.models;

import com.neptuneBank.ENUM.Relationship;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
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

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "userId", referencedColumnName = "userId", unique = true, updatable = false)
    private Users userId;

    @Column(nullable = false)
    private String nomineeName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Relationship nomineeRelationship;

    @Column(nullable = false)
    @Past
    private LocalDate nomineeDateOfBirth;

    @Column(nullable = false, unique = true)
    private String nomineeMobileNumber;

    @Email
    @Column(nullable = false, unique = true)
    private String NomineeEmail;

    @Column(nullable = false, unique = true)
    private String nomineeAadhar;

    @Column(nullable = false, unique = true)
    private String nomineePan;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String nomineeAddress;

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
}
