package com.neptunebank.bankingservice.controller;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.bankingservice.controller
 * Created by: Ashish Kushwaha on 02-06-2025 11:36
 * File: BankController
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */

import com.neptunebank.bankingservice.ENUMs.RecoveryPhrases;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class BankController {
    public ResponseEntity<HashMap<String, String>> login(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam(required = false) RecoveryPhrases recoveryPhrases,
            @RequestParam(required = false) String recoveryAnswer
    ) {
        

        return ResponseEntity.ok(new HashMap<>());
    }
}
