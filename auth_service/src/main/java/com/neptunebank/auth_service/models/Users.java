package com.neptunebank.auth_service.models;

import com.neptunebank.auth_service.ENUM.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.auth_service.models
 * Created by: Ashish Kushwaha on 19-07-2025 17:23
 * File: LoginRequest
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
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotBlank(message = "Username cannot be blank")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    @NotNull(message = "Role cannot be blank")
    @Enumerated(EnumType.STRING)
    private Roles roles;

    @Builder.Default
    private boolean isActive = true;

    @Builder.Default
    private boolean isExpired = false;

    @Builder.Default
    private boolean isLocked = false;

    @Builder.Default
    private boolean isCredentialsExpired = false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + roles.name()));
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !this.isExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !this.isCredentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isActive;
    }
}
