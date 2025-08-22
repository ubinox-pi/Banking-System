package com.neptunebank.user_service.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.user_service.configuration
 * Created by: Ashish Kushwaha on 17-08-2025 11:19
 * File: OpenApiConfig
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
@OpenAPIDefinition(
        info = @Info(
                title = "Neptune User Service",
                version = "1.0.0",
                description = "Central API Documentation for all services",
                contact = @Contact(name = "Support", email = "ashish23481@gmail.com"),
                license = @License(name = "Custom license refer to https://github.com/ubinox-pi/Banking-System/blob/Ramjee-prasad/LICENSE")
        ),
        servers = {
                @Server(url = "http://localhost:8081", description = "Local user-service"),
                @Server(url = "https://neptunebank.online", description = "Production Gateway")
        }
)
public class OpenApiConfig {
}
