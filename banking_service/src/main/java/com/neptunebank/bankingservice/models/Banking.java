package com.neptunebank.bankingservice.models;

import com.neptunebank.bankingservice.ENUMs.RecoveryPhrases;
import jakarta.persistence.*;
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

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecoveryPhrases recoveryPhrase = RecoveryPhrases.WHAT_IS_YOUR_BIRTH_CITY;

    @Column(nullable = false)
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
