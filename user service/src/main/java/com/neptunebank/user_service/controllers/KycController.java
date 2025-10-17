package com.neptunebank.user_service.controllers;

import com.neptunebank.user_service.DTO.kycDTO.KycVerificationDTO;
import com.neptunebank.user_service.services.KycService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.user_service.controllers
 * Created by: Ashish Kushwaha on 17-08-2025 01:03
 * File: KycController
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
@RestController
@RequestMapping("/users/kyc")
public class KycController {

    private KycService kycService;

    @Autowired
    public void setKycService(KycService kycService) {
        this.kycService = kycService;
    }


    @PostMapping("/submit")
    @RolesAllowed({"ADMIN", "EMPLOYEE"})
    public ResponseEntity<?> submitKyc(@RequestBody @Valid KycVerificationDTO kyc) {
        return kycService.doKyc(kyc);
    }
}
