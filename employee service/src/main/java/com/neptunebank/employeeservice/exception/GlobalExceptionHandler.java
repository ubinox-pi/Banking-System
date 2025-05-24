package com.neptunebank.employeeservice.exception;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.employeeservice.exception
 * Created by: Ashish Kushwaha on 24-05-2025 14:18
 * File: GlobalExceptionHandler
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */

import com.neptunebank.employeeservice.exception.entity.EmployeeExceptionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private EmployeeExceptionEntity employeeExceptionEntity;

    @Autowired
    public void setEmployeeExceptionEntity(EmployeeExceptionEntity employeeExceptionEntity) {
        this.employeeExceptionEntity = employeeExceptionEntity;
    }

    @ExceptionHandler(EmployeeException.class)
    public ResponseEntity<EmployeeExceptionEntity> handleEmployeeException(EmployeeException exception) {
        employeeExceptionEntity.setMessage(exception.getMessage());
        employeeExceptionEntity.setErrorCode("BAD_REQUEST");
        employeeExceptionEntity.setErrorType("Employee Exception");
        employeeExceptionEntity.setErrorDescription("Employee registration failed");
        employeeExceptionEntity.setErrorDetails("Invalid employee data provided");
        employeeExceptionEntity.setErrorResolution("Please check the provided data and try again");
        employeeExceptionEntity.setErrorTimestamp(String.valueOf(System.currentTimeMillis()));
        employeeExceptionEntity.setErrorPath("/employee/register");
        employeeExceptionEntity.setErrorStatus("400 BAD REQUEST");
        return new ResponseEntity<>(employeeExceptionEntity, HttpStatus.BAD_REQUEST);
    }

}
