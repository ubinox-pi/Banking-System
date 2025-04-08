/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See LICENSE file for details.
 *
 *
 */

package com.neptuneBank.exception;

import com.neptuneBank.exception.usersException.UserException;
import com.neptuneBank.exception.usersException.entity.ErrorResponse;
import com.neptuneBank.exception.usersException.entity.UserExceptionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private UserExceptionEntity userExceptionEntity;

    @Autowired
    public void setUserExceptionEntity(UserExceptionEntity userExceptionEntity) {
        this.userExceptionEntity = userExceptionEntity;
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<?> handleUserException(UserException ex) {
        userExceptionEntity.setMessage(ex.getMessage());
        userExceptionEntity.setErrorCode("BAD_REQUEST");
        userExceptionEntity.setErrorType("User Exception");
        userExceptionEntity.setErrorDescription("User registration failed");
        userExceptionEntity.setErrorDetails("Invalid user data provided");
        userExceptionEntity.setErrorResolution("Please check the provided data and try again");
        userExceptionEntity.setErrorTimestamp(String.valueOf(System.currentTimeMillis()));
        userExceptionEntity.setErrorPath("/auth/user");
        userExceptionEntity.setErrorStatus("400 BAD REQUEST");
        return new ResponseEntity<>(userExceptionEntity, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse response = new ErrorResponse(
                "Something went wrong: " + ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
