package com.neptunebank.employeeservice.controllers;

import com.neptunebank.employeeservice.models.POJO.KycService;
import com.neptunebank.employeeservice.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.employeeservice.controllers
 * Created by: Ashish Kushwaha on 23-05-2025 20:06
 * File: EmployeeController
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
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/verifyKyc")
    public ResponseEntity<HashMap<String, String>> verifyKyc(@RequestBody KycService kycService) {

        employeeService.verifyUserKyc(kycService.getKycId(), kycService.getEmployeeId());

        HashMap<String, String> response = new HashMap<>();
        response.put("message", "KYC verification Completed");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
