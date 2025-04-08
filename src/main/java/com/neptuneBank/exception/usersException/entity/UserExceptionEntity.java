/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See LICENSE file for details.
 *
 *
 */

package com.neptuneBank.exception.usersException.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
public class UserExceptionEntity {
    private String message;
    private String errorCode;
    private String errorType;
    private String errorDescription;
    private String errorDetails;
    private String errorResolution;
    private String errorTimestamp;
    private String errorPath;
    private String errorStatus;
}
