/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See LICENSE file for details.
 *
 *
 */

package com.neptuneBank.service;

import com.neptuneBank.DTOs.usersDTO.UsersRequestDTO;
import com.neptuneBank.ENUM.MaritalStatus;
import com.neptuneBank.exception.usersException.UserException;
import com.neptuneBank.mapper.UsersMapper;
import com.neptuneBank.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void registerUser(UsersRequestDTO request) throws UserException {
        if (request == null || request.getContactDetails() == null || request.getNominee() == null) {
            throw new UserException("User, contact details and nominee cannot be null.");
        }

        if (request.getMaritalStatus() == MaritalStatus.MARRIED && request.getSpouseName() == null) {
            throw new UserException("Spouse name is required for married users.");
        }

        userRepository.save(UsersMapper.toEntity(request));
    }


}
