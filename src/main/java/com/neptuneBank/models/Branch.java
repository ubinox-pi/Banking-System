/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See LICENSE file for details.
 *
 *
 */

package com.neptuneBank.models;

import com.neptuneBank.DTOs.branchDTO.BranchDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Branch {

    @Id
    private String branchId;

    @NotBlank(message = "Swift Id is required.")
    @Column(nullable = false)
    private String swiftId;

    @NotBlank(message = "Branch name is required.")
    @Column(nullable = false)
    private String branchName;

    @NotBlank(message = "Branch address is required.")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String branchAddress;

    @NotBlank(message = "Branch city is required.")
    @Column(nullable = false)
    private String branchCity;

    @NotBlank(message = "Branch state is required.")
    @Column(nullable = false)
    private String branchState;

    @NotBlank(message = "Branch pin is required.")
    @Column(nullable = false)
    private String branchZip;

    @NotBlank(message = "Branch country is required.")
    @Column(nullable = false)
    private String branchCountry;

    @Column(nullable = false, updatable = false)
    @FutureOrPresent
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @PastOrPresent
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }


    // DTO implementation
    public void fromDTO(BranchDTO dto) {
        this.branchId = dto.getBranchId();
        this.swiftId = dto.getSwiftId();
        this.branchName = dto.getBranchName();
        this.branchAddress = dto.getBranchAddress();
        this.branchCity = dto.getBranchCity();
        this.branchState = dto.getBranchState();
        this.branchZip = dto.getBranchZip();
        this.branchCountry = dto.getBranchCountry();
    }

    public BranchDTO toDTO() {
        return BranchDTO.builder()
                .branchId(this.branchId)
                .swiftId(this.swiftId)
                .branchName(this.branchName)
                .branchAddress(this.branchAddress)
                .branchCity(this.branchCity)
                .branchState(this.branchState)
                .branchZip(this.branchZip)
                .branchCountry(this.branchCountry)
                .build();
    }

    public void toDTOId(BranchDTO dto) {
        this.branchId = dto.getBranchId();
    }

    public BranchDTO toDTOId() {
        return BranchDTO.builder()
                .branchId(this.branchId)
                .build();
    }

    public BranchDTO toAdminDTO() {
        return BranchDTO.builder()
                .branchId(this.branchId)
                .swiftId(this.swiftId)
                .branchName(this.branchName)
                .branchAddress(this.branchAddress)
                .branchCity(this.branchCity)
                .branchState(this.branchState)
                .branchZip(this.branchZip)
                .branchCountry(this.branchCountry)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }
}
