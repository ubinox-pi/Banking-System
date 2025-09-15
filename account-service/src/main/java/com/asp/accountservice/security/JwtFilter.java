package com.asp.accountservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JWT filter: validates token, builds Authentication with principal = AuthenticatedPrincipal.
 */
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    public JwtFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        String header = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                Jws<Claims> jws = jwtUtils.parseToken(token);
                Claims claims = jws.getBody();

                String username = claims.getSubject();

                // Extract userId safely — token may store it as Integer, Long or String
                Long userId = null;
                Object uidObj = claims.get("userId");
                if (uidObj != null) {
                    if (uidObj instanceof Number) {
                        userId = ((Number) uidObj).longValue();
                    } else {
                        try {
                            userId = Long.valueOf(String.valueOf(uidObj));
                        } catch (NumberFormatException ignored) { /* leave null */ }
                    }
                }

                // Extract role(s). Your auth currently stores "role" as single string, adjust if different.
                Object rolesObj = claims.get("role");
                List<String> roles;
                if (rolesObj instanceof List) {
                    //noinspection unchecked
                    roles = (List<String>) rolesObj;
                } else if (rolesObj != null) {
                    roles = List.of(String.valueOf(rolesObj));
                } else {
                    roles = List.of();
                }

                var authorities = roles.stream()
                        .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                        .collect(Collectors.toList());

                var principal = new AuthenticatedPrincipal(username, userId);
                var auth = new UsernamePasswordAuthenticationToken(principal, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (Exception ex) {
                // invalid token -> ensure no authentication is set
                SecurityContextHolder.clearContext();
            }
        }

        chain.doFilter(req, res);
    }
}
