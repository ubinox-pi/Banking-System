package com.asp.userservice.listener;

/*
 * Copyright (c) 2025 Ayshi Shannidhya Panda. All rights reserved.
 *
 * This source code is confidential and intended solely for internal use.
 * Unauthorized copying, modification, distribution, or disclosure of this
 * file, via any medium, is strictly prohibited.
 *
 * Project: Neptune Bank
 * Author: Ayshi Shannidhya Panda
 * Created on: 06-09-2025
 */

import com.asp.userservice.repositories.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class UserValidationListener {

    private final UserRepository userRepository;

    public UserValidationListener(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RabbitListener(queues = "${rabbitmq.user.validation.request.queue}")
    public Boolean validateUser(Long userId) {
        if (userId == null) return false;
        return userRepository.existsByUserid(userId);
    }
}
