package com.neptunebank.account_service.controller;

import com.neptunebank.account_service.dto.branchDTO.BranchesDTO;
import com.neptunebank.account_service.service.BranchService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.account_service.controller
 * Created by: Ashish Kushwaha on 29-07-2025 12:58
 * File: BranchController
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
@Controller
@RequestMapping("/branch")
public class BranchController {

    private BranchService branchService;

    @Autowired
    public void setBranchService(BranchService branchService) {
        this.branchService = branchService;
    }

    @PostMapping("/create")
    @RolesAllowed({"ADMIN"})
    public ResponseEntity<?> createBranch(@RequestBody BranchesDTO branchesDTO) {
        return branchService.addBranch(branchesDTO);
    }
}
