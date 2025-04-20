/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See LICENSE file for details.
 *
 *
 */

package com.neptuneBank.exception;

import com.neptuneBank.exception.usersException.UserException;
import com.neptuneBank.exception.usersException.entity.ContactDetailsExceptionEntity;
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
    private ContactDetailsExceptionEntity contactDetailsExceptionEntity;

    @Autowired
    public void setContactDetailsException(ContactDetailsExceptionEntity contactDetailsExceptionEntity) {
        this.contactDetailsExceptionEntity = contactDetailsExceptionEntity;
    }

    @Autowired
    public void setUserExceptionEntity(UserExceptionEntity userExceptionEntity) {
        this.userExceptionEntity = userExceptionEntity;
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<?> handleUserException(UserException ex) {
        contactDetailsExceptionEntity.setMessage(ex.getMessage());
        contactDetailsExceptionEntity.setErrorCode("BAD_REQUEST");
        contactDetailsExceptionEntity.setErrorType("User Exception");
        contactDetailsExceptionEntity.setErrorDescription("User registration failed");
        contactDetailsExceptionEntity.setErrorDetails("Invalid user data provided");
        contactDetailsExceptionEntity.setErrorResolution("Please check the provided data and try again");
        contactDetailsExceptionEntity.setErrorTimestamp(String.valueOf(System.currentTimeMillis()));
        contactDetailsExceptionEntity.setErrorPath("/auth/user");
        contactDetailsExceptionEntity.setErrorStatus("400 BAD REQUEST");
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
