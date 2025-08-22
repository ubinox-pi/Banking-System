package com.neptunebank.neptunebank.component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.neptunebank.component
 * Created by: Ashish Kushwaha on 20-07-2025 14:04
 * File: Authentication
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
@Service
public class AuthenticationFilter implements GlobalFilter, Ordered {

    private final WebClient webClient;
    private final String validateUrl;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Value("${secret.key}")
    private String secret;

    public AuthenticationFilter(@Value("${auth-service.url}") String authServiceUrl) {
        this.webClient = WebClient.create();
        this.validateUrl = authServiceUrl + "/auth/validate";
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if (path.startsWith("/auth/") ||
                path.startsWith("/public/") ||
                path.contains("swagger") ||
                path.contains("api-docs") ||
                path.contains("/health") ||
                path.contains("/actuator")) {
            return chain.filter(exchange);
        }
        ServerHttpRequest request = exchange.getRequest();
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String cookieHeader = request.getHeaders().getFirst(HttpHeaders.COOKIE);

        return webClient.get()
                .uri(validateUrl)
                .headers(headers -> {
                    if (authHeader != null) headers.set(HttpHeaders.AUTHORIZATION, authHeader);
                    if (cookieHeader != null) headers.set(HttpHeaders.COOKIE, cookieHeader);
                    if (secret != null) headers.set("X-Secret-Key", secret);
                })
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> Mono.error(new RuntimeException("Unauthorized")))
                .bodyToMono(String.class)
                .flatMap(responseBody -> {
                    ServerHttpRequest mutatedRequest = request.mutate()
                            .header("X-Authenticated-User", extractUser(responseBody))
                            .header("X-Authenticated-Role", extractRole(responseBody))
                            .build();
                    return chain.filter(exchange.mutate().request(mutatedRequest).build());
                })
                .onErrorResume(error -> {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                });
    }

    private String extractUser(String body) {
        try {
            JsonNode node = objectMapper.readTree(body);
            return node.get("username").asText();
        } catch (Exception e) {
            return "";
        }
    }

    private String extractRole(String body) {
        try {
            JsonNode node = objectMapper.readTree(body);
            return node.get("role").asText();
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
