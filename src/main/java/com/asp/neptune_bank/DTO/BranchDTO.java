package com.asp.neptune_bank.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchDTO {
    private Long branchId;
    private String branchName;
    private String branchAddress;
    private String branchCity;
    private String branchState;
    private String branchZip;
}
