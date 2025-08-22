package com.neptunebank.user_service.models.log;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.user_service.models.log
 * Created by: Ashish Kushwaha on 27-07-2025 22:29
 * File: ApiCallLog
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
@Document(collection = "api_call_logs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiCallLog {
    @Id
    private String id;

    private LocalDateTime timestamp;
    private String httpMethod;
    private String url;
    private String queryString;
    private Map<String, String> requestHeaders;
    private String requestBody;
    private String contentType;
    private String clientIp;
    private String userAgent;
    private String referrer;
    private String sessionId;
    private String jwtToken;
    private String username;

    private int responseStatus;
    private String responseBody;
    private long durationMs;

    private long requestSizeBytes;
    private long responseSizeBytes;

    private String controllerClass;
    private String controllerMethod;
    private String threadName;
    private String appVersion;
    private boolean isError;
}
