package com.neptunebank.neptunebank.config;

import com.neptunebank.neptunebank.models.Users;
import com.neptunebank.neptunebank.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.net.URI;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.neptunebank.Config
 * Created by: Ashish Kushwaha on 14-07-2025 20:20
 * File: Security
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private AdminRepository adminRepository;

    @Autowired
    public void setAdminRepository(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(
                                "/web/auth/login",
                                "/web/auth/logout",
                                "/css/**",
                                "/js/**",
                                "/images/**").permitAll()
                        .anyExchange().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/web/auth/login")
                        .authenticationSuccessHandler((webFilterExchange, authentication) -> {
                            webFilterExchange.getExchange().getResponse().setStatusCode(HttpStatus.FOUND);
                            webFilterExchange.getExchange().getResponse().getHeaders().setLocation(URI.create("/dashboard"));
                            return webFilterExchange.getExchange().getResponse().setComplete();
                        })
                        .authenticationFailureHandler((webFilterExchange, exception) -> {
                            webFilterExchange.getExchange().getResponse().setStatusCode(HttpStatus.FOUND);
                            webFilterExchange.getExchange().getResponse().getHeaders().setLocation(URI.create("/login?error"));
                            return webFilterExchange.getExchange().getResponse().setComplete();
                        })
                )
                .logout(logout -> logout
                        .logoutUrl("/web/auth/logout")
                        .logoutSuccessHandler((exchange, authentication) -> {
                            exchange.getExchange().getResponse().setStatusCode(HttpStatus.FOUND);
                            exchange.getExchange().getResponse().getHeaders().setLocation(URI.create("/login?logout"));
                            return exchange.getExchange().getResponse().setComplete();
                        })
                );
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Users user = adminRepository.findByUsername(username);
            if (user == null) {
                throw new RuntimeException("User not found: " + username);
            }
            return (UserDetails) user;
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
