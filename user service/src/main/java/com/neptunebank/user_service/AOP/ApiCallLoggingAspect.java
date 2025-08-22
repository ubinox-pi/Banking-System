package com.neptunebank.user_service.AOP;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neptunebank.user_service.models.log.ApiCallLog;
import com.neptunebank.user_service.services.LoggingDispatcherService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.user_service.AOP
 * Created by: Ashish Kushwaha on 27-07-2025 22:33
 * File: ApiCallLoggingAspect
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ApiCallLoggingAspect {
    private ObjectMapper objectMapper;
    private LoggingDispatcherService loggingDispatcherService;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    public void setLoggingDispatcherService(LoggingDispatcherService loggingDispatcherService) {
        this.loggingDispatcherService = loggingDispatcherService;
    }

//    @Around("execution(* com.neptunebank.user_service.controllers..*(..))")
//    public Object logApiCall(ProceedingJoinPoint joinPoint) throws Throwable {
//        long startTime = System.currentTimeMillis();
//
//        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        if (servletRequestAttributes == null)
//            return joinPoint.proceed();
//
//        HttpServletRequest request = servletRequestAttributes.getRequest();
//        HttpServletResponse response = servletRequestAttributes.getResponse();
//
//        String method = request.getMethod();
//        String url = request.getRequestURL().toString();
//        String query = request.getQueryString();
//        String contentType = request.getContentType();
//        String clientIp = request.getRemoteAddr();
//        String userAgent = request.getHeader("User-Agent");
//        String referrer = request.getHeader("Referer");
//        String sessionId = request.getRequestedSessionId();
//        String token = request.getHeader("Authorization");
//
//        String requestBody = extractRequestBody(request);
//        Map<String, String> requestHeaders = extractHeaders(request);
//
//        Object result;
//        int status = 0;
//        String responseBody = null;
//
//        try {
//            result = joinPoint.proceed();
//            if (response != null) {
//                status = response.getStatus();
//            }
//            if (result != null) {
//                responseBody = objectMapper.writeValueAsString(result);
//            }
//        } catch (Exception e) {
//            log.error("Exception in API call", e);
//            status = 500;
//            responseBody = e.getMessage();
//            throw e;
//        } finally {
//            long duration = System.currentTimeMillis() - startTime;
//
//            ApiCallLog logEntry = ApiCallLog.builder()
//                    .timestamp(LocalDateTime.now())
//                    .httpMethod(method)
//                    .url(url)
//                    .queryString(query)
//                    .requestHeaders(requestHeaders)
//                    .requestBody(requestBody)
//                    .contentType(contentType)
//                    .clientIp(clientIp)
//                    .userAgent(userAgent)
//                    .referrer(referrer)
//                    .sessionId(sessionId)
//                    .jwtToken(token)
//                    .username(request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : "anonymous")
//                    .responseStatus(status)
//                    .responseBody(responseBody)
//                    .durationMs(duration)
//                    .requestSizeBytes(requestBody != null ? requestBody.getBytes().length : 0)
//                    .responseSizeBytes(responseBody != null ? responseBody.getBytes().length : 0)
//                    .controllerClass(joinPoint.getSignature().getDeclaringTypeName())
//                    .controllerMethod(joinPoint.getSignature().getName())
//                    .threadName(Thread.currentThread().getName())
//                    .appVersion("v1.0.0") // Optional: load from env or props
//                    .isError(status >= 400)
//                    .build();
//            loggingDispatcherService.saveApiCallLog(logEntry);
//        }
//
//        return result;
//
//    }
//
//    private Map<String, String> extractHeaders(HttpServletRequest request) {
//        Map<String, String> headers = new HashMap<>();
//        Enumeration<String> headerNames = request.getHeaderNames();
//        if (headerNames == null) return headers;
//
//        while (headerNames.hasMoreElements()) {
//            String key = headerNames.nextElement();
//            headers.put(key, request.getHeader(key));
//        }
//        return headers;
//    }
//
//    private String extractRequestBody(HttpServletRequest request) {
//        try {
//            BufferedReader reader = request.getReader();
//            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
//        } catch (IOException e) {
//            return "Failed to read request body: " + e.getMessage();
//        }
//    }

    @Around("execution(* com.neptunebank.user_service.controllers..*(..))")
    public Object logApiCall(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return joinPoint.proceed();
        }

        HttpServletRequest request = wrapRequest(attributes.getRequest());
        HttpServletResponse response = wrapResponse(attributes.getResponse());

        String requestBody = getRequestBody(request);
        Map<String, String> requestHeaders = getHeaders(request);

        Object result;
        String responseBody = null;
        int status = 0;

        try {
            result = joinPoint.proceed();
            status = response.getStatus();
            responseBody = getResponseBody(response, result);
        } catch (Exception e) {
            log.error("Exception in API call", e);
            status = 500;
            responseBody = e.getMessage();
            throw e;
        } finally {
            long duration = System.currentTimeMillis() - startTime;

            ApiCallLog logEntry = ApiCallLog.builder()
                    .timestamp(LocalDateTime.now())
                    .httpMethod(request.getMethod())
                    .url(request.getRequestURL().toString())
                    .queryString(request.getQueryString())
                    .requestHeaders(requestHeaders)
                    .requestBody(requestBody)
                    .contentType(request.getContentType())
                    .clientIp(request.getRemoteAddr())
                    .userAgent(request.getHeader("User-Agent"))
                    .referrer(request.getHeader("Referer"))
                    .sessionId(request.getRequestedSessionId())
                    .jwtToken(request.getHeader("Authorization"))
                    .username(request.getUserPrincipal() != null ?
                            request.getUserPrincipal().getName() : "anonymous")
                    .responseStatus(response.getStatus())
                    .responseBody(responseBody)
                    .durationMs(duration)
                    .requestSizeBytes(requestBody != null ? requestBody.getBytes().length : 0)
                    .responseSizeBytes(responseBody != null ? responseBody.getBytes().length : 0)
                    .controllerClass(joinPoint.getSignature().getDeclaringTypeName())
                    .controllerMethod(joinPoint.getSignature().getName())
                    .threadName(Thread.currentThread().getName())
                    .appVersion("v1.0.0")
                    .isError(status >= 400)
                    .build();

            loggingDispatcherService.saveApiCallLog(logEntry);

            if (response instanceof ContentCachingResponseWrapper) {
                ((ContentCachingResponseWrapper) response).copyBodyToResponse();
            }
        }
        return result;
    }

    private HttpServletRequest wrapRequest(HttpServletRequest request) {
        return request instanceof ContentCachingRequestWrapper
                ? request
                : new ContentCachingRequestWrapper(request);
    }

    private HttpServletResponse wrapResponse(HttpServletResponse response) {
        return response instanceof ContentCachingResponseWrapper
                ? response
                : new ContentCachingResponseWrapper(response);
    }

    private String getRequestBody(HttpServletRequest request) {
        if (request.getContentType() != null &&
                request.getContentType().startsWith("multipart/form-data")) {
            return "[MULTIPART DATA]";
        }
        byte[] buf = ((ContentCachingRequestWrapper) request).getContentAsByteArray();
        return buf.length > 0 ? new String(buf, StandardCharsets.UTF_8) : null;
    }

    private String getResponseBody(HttpServletResponse response, Object result) throws Exception {
        byte[] buf = ((ContentCachingResponseWrapper) response).getContentAsByteArray();
        if (buf.length > 0) {
            return new String(buf, StandardCharsets.UTF_8);
        } else if (result != null) {
            return objectMapper.writeValueAsString(result);
        }
        return null;
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames != null && headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            headers.put(key, request.getHeader(key));
        }
        return headers;
    }
}
