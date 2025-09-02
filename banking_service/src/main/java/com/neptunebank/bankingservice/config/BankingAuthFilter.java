package com.neptunebank.bankingservice.config;

import com.neptunebank.bankingservice.jwt.JwtUtil;
import com.neptunebank.bankingservice.services.BankingSessionService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.account_service.config
 * Created by: Ashish Kushwaha on 25-08-2025 16:45
 * File: SessionFilter
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
public class BankingAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final BankingSessionService sessionService;

    public BankingAuthFilter(JwtUtil jwtUtil, BankingSessionService sessionService) {
        this.jwtUtil = jwtUtil;
        this.sessionService = sessionService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing Gateway authentication");
            return;
        }

        String bankingJwt = request.getHeader("X-Banking-Auth");
        if (bankingJwt != null) {
            Claims claims = jwtUtil.validateToken(bankingJwt);
            String username = claims.getSubject();
            String role = claims.get("role", String.class);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            List.of(new SimpleGrantedAuthority("ROLE_" + role))
                    );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
            return;
        }


        if (sessionService.isValidSession(request)) {
            HttpSession session = request.getSession(false);
            String username = (String) session.getAttribute("username");
            String role = (String) session.getAttribute("role");
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            List.of(new SimpleGrantedAuthority("ROLE_" + role))
                    );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
            return;
        }

        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Banking authentication required");
    }
}

