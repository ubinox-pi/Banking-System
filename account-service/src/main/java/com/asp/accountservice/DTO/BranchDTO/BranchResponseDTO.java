package com.asp.accountservice.DTO.BranchDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchResponseDTO {
    private Long branchId;
    private String branchCode;
    private String branchName;
    private String branchAddress;
    private String branchCity;
    private String branchState;
    private String branchZip;
    private boolean success;
    private String message;
}
