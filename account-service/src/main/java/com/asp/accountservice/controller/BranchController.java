package com.asp.accountservice.controller;

import com.asp.accountservice.DTO.BranchDTO.BranchRequestDTO;
import com.asp.accountservice.DTO.BranchDTO.BranchResponseDTO;
import com.asp.accountservice.repositories.BranchRepository;
import com.asp.accountservice.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/branches")
@RequiredArgsConstructor
public class BranchController {


    private final BranchService branchService;
    private final BranchRepository branchRepository;


    @PostMapping("/create")
    public ResponseEntity<BranchResponseDTO> createBranch(@RequestBody BranchRequestDTO requestDTO) {
        BranchResponseDTO response = branchService.createBranch(requestDTO);

        if (!response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

