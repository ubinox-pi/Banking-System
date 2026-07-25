package com.neptunebank.transaction_service.DTOs;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: ConvoBackend
 * Package: com.convo.dto
 * Created by: Ashish Kushwaha on 29-12-2025 02:00
 * File: ApiResponse
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    @Builder.Default
    private String timestamp = Instant.now().toString();

    private int status;

    private String statusText;

    private boolean success;

    private String message;

    private T data;

    private String error;

    private String errorCode;

    private List<FieldError> fieldErrors;

    private Pagination pagination;

    private String path;

    private String requestId;

    private Long responseTimeMs;

    private String action;

    private String redirectUrl;

    private Map<String, Object> meta;

    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .timestamp(Instant.now().toString())
                .status(HttpStatus.OK.value())
                .statusText(HttpStatus.OK.getReasonPhrase())
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success(T data, String message, String path) {
        return ApiResponse.<T>builder()
                .timestamp(Instant.now().toString())
                .status(HttpStatus.OK.value())
                .statusText(HttpStatus.OK.getReasonPhrase())
                .success(true)
                .message(message)
                .data(data)
                .path(path)
                .build();
    }

    public static <T> ApiResponse<T> created(T data, String message, String path) {
        return ApiResponse.<T>builder()
                .timestamp(Instant.now().toString())
                .status(HttpStatus.CREATED.value())
                .statusText(HttpStatus.CREATED.getReasonPhrase())
                .success(true)
                .message(message)
                .data(data)
                .path(path)
                .build();
    }

    public static <T> ApiResponse<T> success(String message) {
        return ApiResponse.<T>builder()
                .timestamp(Instant.now().toString())
                .status(HttpStatus.OK.value())
                .statusText(HttpStatus.OK.getReasonPhrase())
                .success(true)
                .message(message)
                .build();
    }

    public static <T> ApiResponse<T> error(HttpStatus status, String error, String message, String path) {
        return ApiResponse.<T>builder()
                .timestamp(Instant.now().toString())
                .status(status.value())
                .statusText(status.getReasonPhrase())
                .success(false)
                .error(error)
                .message(message)
                .path(path)
                .build();
    }

    public static <T> ApiResponse<T> error(HttpStatus status, String error, String errorCode, String message, String path) {
        return ApiResponse.<T>builder()
                .timestamp(Instant.now().toString())
                .status(status.value())
                .statusText(status.getReasonPhrase())
                .success(false)
                .error(error)
                .errorCode(errorCode)
                .message(message)
                .path(path)
                .build();
    }

    public static <T> ApiResponse<T> validationError(String message, List<FieldError> fieldErrors, String path) {
        return ApiResponse.<T>builder()
                .timestamp(Instant.now().toString())
                .status(HttpStatus.BAD_REQUEST.value())
                .statusText(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .success(false)
                .error("Validation Failed")
                .errorCode("VALIDATION_ERROR")
                .message(message)
                .fieldErrors(fieldErrors)
                .path(path)
                .build();
    }

    public static <T> ApiResponse<T> errorWithAction(HttpStatus status, String error, String message, String action, String path) {
        return ApiResponse.<T>builder()
                .timestamp(Instant.now().toString())
                .status(status.value())
                .statusText(status.getReasonPhrase())
                .success(false)
                .error(error)
                .message(message)
                .action(action)
                .path(path)
                .build();
    }

    public static <T> ApiResponse<T> paginated(T data, Pagination pagination, String path) {
        return ApiResponse.<T>builder()
                .timestamp(Instant.now().toString())
                .status(HttpStatus.OK.value())
                .statusText(HttpStatus.OK.getReasonPhrase())
                .success(true)
                .message("Data retrieved successfully")
                .data(data)
                .pagination(pagination)
                .path(path)
                .build();
    }

    public static <T> ApiResponse<T> unauthorized(String message, String path) {
        return ApiResponse.<T>builder()
                .timestamp(Instant.now().toString())
                .status(HttpStatus.UNAUTHORIZED.value())
                .statusText(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .success(false)
                .error("Unauthorized")
                .errorCode("AUTH_REQUIRED")
                .message(message)
                .action("REDIRECT_LOGIN")
                .redirectUrl("/login")
                .path(path)
                .build();
    }

    public static <T> ApiResponse<T> forbidden(String message, String path) {
        return ApiResponse.<T>builder()
                .timestamp(Instant.now().toString())
                .status(HttpStatus.FORBIDDEN.value())
                .statusText(HttpStatus.FORBIDDEN.getReasonPhrase())
                .success(false)
                .error("Access Denied")
                .errorCode("ACCESS_DENIED")
                .message(message)
                .path(path)
                .build();
    }

    public static <T> ApiResponse<T> notFound(String message, String path) {
        return ApiResponse.<T>builder()
                .timestamp(Instant.now().toString())
                .status(HttpStatus.NOT_FOUND.value())
                .statusText(HttpStatus.NOT_FOUND.getReasonPhrase())
                .success(false)
                .error("Not Found")
                .errorCode("RESOURCE_NOT_FOUND")
                .message(message)
                .path(path)
                .build();
    }

    public static <T> ApiResponse<T> rateLimitExceeded(String path, long retryAfterSeconds) {
        return ApiResponse.<T>builder()
                .timestamp(Instant.now().toString())
                .status(HttpStatus.TOO_MANY_REQUESTS.value())
                .statusText(HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase())
                .success(false)
                .error("Rate Limit Exceeded")
                .errorCode("RATE_LIMIT_EXCEEDED")
                .message("Too many requests. Please try again later.")
                .action("RETRY")
                .meta(Map.of("retryAfterSeconds", retryAfterSeconds))
                .path(path)
                .build();
    }

    public static <T> ApiResponse<T> sessionExpired(String path) {
        return ApiResponse.<T>builder()
                .timestamp(Instant.now().toString())
                .status(HttpStatus.UNAUTHORIZED.value())
                .statusText(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .success(false)
                .error("Session Expired")
                .errorCode("SESSION_EXPIRED")
                .message("Your session has expired. Please login again.")
                .action("REDIRECT_LOGIN")
                .redirectUrl("/login")
                .path(path)
                .build();
    }

    public static <T> ApiResponse<T> internalError(String requestId, String path) {
        return ApiResponse.<T>builder()
                .timestamp(Instant.now().toString())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .statusText(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .success(false)
                .error("Internal Server Error")
                .errorCode("INTERNAL_ERROR")
                .message("An unexpected error occurred. Please try again later.")
                .requestId(requestId)
                .action("RETRY")
                .path(path)
                .build();
    }

    @Data
    @Builder
    public static class FieldError {
        private String field;
        private String message;
        private Object rejectedValue;
        private String code;
    }

    @Data
    @Builder
    public static class Pagination {
        private int currentPage;
        private int pageSize;
        private long totalElements;
        private int totalPages;
        private boolean hasNext;
        private boolean hasPrevious;
        private boolean isFirst;
        private boolean isLast;
    }
}
