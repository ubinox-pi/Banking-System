package com.neptunebank.employeeservice.exception.entity;

import lombok.*;
import org.springframework.stereotype.Component;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.employeeservice.exception.entity
 * Created by: Ashish Kushwaha on 24-05-2025 14:20
 * File: ExceptionEntity
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class EmployeeExceptionEntity {
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
