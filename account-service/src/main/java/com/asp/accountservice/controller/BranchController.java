package com.asp.accountservice.controller;

import com.asp.accountservice.DTO.BranchDTO.BranchDTO;
import com.asp.accountservice.models.Branch;
import com.asp.accountservice.repositories.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchRepository branchRepository;

    @PostMapping("/create")
    public ResponseEntity<BranchDTO> createBranch(@RequestBody BranchDTO branchDTO) {
        Branch branch = Branch.fromDTO(branchDTO);
        Branch saved = branchRepository.save(branch);
        return ResponseEntity.ok(saved.toDTO());
    }
}

