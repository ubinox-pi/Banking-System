package com.neptunebank.bankingservice.config;

import com.neptunebank.bankingservice.jwt.JwtUtil;
import com.neptunebank.bankingservice.services.BankingSessionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.bankingservice.config
 * Created by: Ashish Kushwaha on 16-09-2025 10:05
 * File: Configs
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
public class Configs {

    @Bean
    protected BankingAuthFilter bankingAuthFilter(JwtUtil jwtUtil, BankingSessionService sessionService) {
        return new BankingAuthFilter(jwtUtil, sessionService);
    }

    @Bean
    protected HeaderRoleAuthenticationFilter headerRoleAuthenticationFilter() {
        return new HeaderRoleAuthenticationFilter();
    }
}
