package com.neptunebank.bankingservice.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.bankingservice.services
 * Created by: Ashish Kushwaha on 02-09-2025 10:52
 * File: BankingSessionService
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
public class BankingSessionService {

    public HttpSession createSession(HttpServletRequest request) {
        return request.getSession(true);
    }

    public boolean isValidSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null;
    }
}
