package com.neptunebank.user_service.services;

import com.neptunebank.user_service.models.log.ApiCallLog;
import com.neptunebank.user_service.repositories.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.user_service.services
 * Created by: Ashish Kushwaha on 27-07-2025 22:49
 * File: LoggingDispatcherService
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
@Service
public class LoggingDispatcherService {
    private LogRepository logRepository;

    @Autowired
    public void setLogRepository(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Async
    public void saveApiCallLog(ApiCallLog log) {
        logRepository.save(log);
    }
}
