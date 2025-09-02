package com.neptunebank.bankingservice.models;

import com.neptunebank.bankingservice.ENUMs.RecoveryPhrases;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.bankingservice.models
 * Created by: Ashish Kushwaha on 02-06-2025 11:43
 * File: Bank
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Banking implements UserDetails {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bankId;

    @Column(unique = true, nullable = false)
    private Long userId;

    @Pattern(regexp = "^[aA-zZ0-9_]{5,20}$", message = "Username must be 5-20 characters long and can contain letters, numbers, and underscores only")
    @Column(unique = true, nullable = false)
    private String username;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one number, and one special character")
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private RecoveryPhrases recoveryPhrase = RecoveryPhrases.WHAT_IS_YOUR_BIRTH_CITY;

    @Column(nullable = false)
    @Builder.Default
    private String recoveryAnswer = "JAMSHEDPUR";

    @Builder.Default
    private Boolean isAccountIsNotExpired = false;

    @Builder.Default
    private Boolean isAccountIsNotLocked = false;

    @Builder.Default
    private Boolean isCredentialsIsNotExpired = false;

    @Builder.Default
    private Boolean isActive = false;

    @Builder.Default
    private Boolean isFirstLoggedInSuccess = false;


    @OneToMany(
            mappedBy = "banking",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @OrderBy("loginTime DESC")
    @Builder.Default
    private List<LoginHistory> loginHistories = new ArrayList<>();

    @Builder.Default
    private String history = null;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + "USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountIsNotExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountIsNotLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsIsNotExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isActive;
    }
}
