package com.neptunebank.employeeservice.models;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.employeeservice.models
 * Created by: Ashish Kushwaha on 23-05-2025 13:01
 * File: Employee
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */

import com.neptunebank.employeeservice.ENUMs.BankRole;
import com.neptunebank.employeeservice.ENUMs.Genders;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long employeeId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String middleName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String mobileNumber;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BankRole roles;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genders gender;

    @Builder.Default
    private Boolean isAccountNonExpired = true;

    @Builder.Default
    private Boolean isAccountNonLocked = true;

    @Builder.Default
    private Boolean isCredentialsNonExpired = true;

    @Builder.Default
    private Boolean isEnabled = true;

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

        if (firstName != null)
            if (!firstName.equals(firstName.toUpperCase()))
                firstName = firstName.toUpperCase();

        if (middleName != null)
            if (!middleName.equals(middleName.toUpperCase()))
                middleName = middleName.toUpperCase();

        if (lastName != null)
            if (!lastName.equals(lastName.toUpperCase()))
                lastName = lastName.toUpperCase();

        if (email != null)
            if (!email.equals(email.toLowerCase()))
                email = email.toLowerCase();

        if (username != null)
            if (!username.equals(username.toLowerCase()))
                username = username.toLowerCase();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();

        if (firstName != null)
            if (!firstName.equals(firstName.toUpperCase()))
                firstName = firstName.toUpperCase();

        if (middleName != null)
            if (!middleName.equals(middleName.toUpperCase()))
                middleName = middleName.toUpperCase();

        if (lastName != null)
            if (!lastName.equals(lastName.toUpperCase()))
                lastName = lastName.toUpperCase();

        if (email != null)
            if (!email.equals(email.toLowerCase()))
                email = email.toLowerCase();

        if (username != null)
            if (!username.equals(username.toLowerCase()))
                username = username.toLowerCase();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton((GrantedAuthority) () -> this.roles.name());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}
