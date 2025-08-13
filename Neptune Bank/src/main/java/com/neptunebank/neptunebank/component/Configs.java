package com.neptunebank.neptunebank.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.neptunebank.component
 * Created by: Ashish Kushwaha on 26-07-2025 02:46
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
    @Value("${auth-service.url}")
    private String authServiceUrl;

    @Bean
    public GlobalFilter authenticationFilter() {
        return new AuthenticationFilter(authServiceUrl);
    }
}
