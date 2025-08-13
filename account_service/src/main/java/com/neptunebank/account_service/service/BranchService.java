package com.neptunebank.account_service.service;

import com.neptunebank.account_service.dto.branchDTO.BranchesDTO;
import com.neptunebank.account_service.mappers.BranchMapper;
import com.neptunebank.account_service.models.Branches;
import com.neptunebank.account_service.repositories.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.account_service.service
 * Created by: Ashish Kushwaha on 29-07-2025 12:36
 * File: BranchService
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
public class BranchService {
    private BranchRepository branchRepository;

    @Autowired
    public void setBranchRepository(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public ResponseEntity<?> addBranch(BranchesDTO branchesDTO) {
        Map<String, String> response = new HashMap<>();
        Branches branches = BranchMapper.toEntity(branchesDTO);
        if (branchRepository.existsByBranchCode(branches.getBranchCode())) {
            response.put("message", "Branch with this code already exists");
            response.put("status", "error");
            response.put("code", "400");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            branchRepository.save(branches);
            response.put("message", "Branch added successfully");
            response.put("status", "success");
            response.put("code", "201");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
    }

    public ResponseEntity<?> getBranchByCode(String branchCode) {
        Map<String, String> response = new HashMap<>();
        Branches branch = branchRepository.findByBranchCode(branchCode);
        if (branch == null) {
            response.put("message", "Branch not found");
            response.put("status", "error");
            response.put("code", "404");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(BranchMapper.toDto(branch), HttpStatus.OK);
        }
    }

    public ResponseEntity<?> getAllBranches() {
        Map<String, Object> response = new HashMap<>();
        var branchesList = branchRepository.findAll();
        if (branchesList.isEmpty()) {
            response.put("message", "No branches found");
            response.put("status", "error");
            response.put("code", "404");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            response.put("branches", branchesList);
            response.put("status", "success");
            response.put("code", "200");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    public ResponseEntity<?> updateBranch(Long branchId, BranchesDTO branchesDTO) {
        Map<String, String> response = new HashMap<>();
        Branches existingBranch = branchRepository.findById(branchId).orElse(null);
        if (existingBranch == null) {
            response.put("message", "Branch not found");
            response.put("status", "error");
            response.put("code", "404");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            existingBranch.setBranchName(branchesDTO.getBranchName());
            existingBranch.setBranchCode(branchesDTO.getBranchCode());
            existingBranch.setBranchAddress(branchesDTO.getBranchAddress());
            branchRepository.save(existingBranch);
            response.put("message", "Branch updated successfully");
            response.put("status", "success");
            response.put("code", "200");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    public ResponseEntity<?> deleteBranch(Long branchId) {
        Map<String, String> response = new HashMap<>();
        if (!branchRepository.existsById(branchId)) {
            response.put("message", "Branch not found");
            response.put("status", "error");
            response.put("code", "404");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            branchRepository.deleteById(branchId);
            response.put("message", "Branch deleted successfully");
            response.put("status", "success");
            response.put("code", "200");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
    
}
