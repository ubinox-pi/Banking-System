package com.asp.accountservice.DTO.BranchDTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchDTO {
    private Long branchId;
    private String branchCode;
    private String branchName;
    private String branchAddress;
    private String branchCity;
    private String branchState;
    private String branchZip;
}
