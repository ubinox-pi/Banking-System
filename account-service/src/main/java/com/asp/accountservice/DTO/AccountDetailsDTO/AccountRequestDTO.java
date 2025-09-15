package com.asp.accountservice.DTO.AccountDetailsDTO;

import com.asp.accountservice.enumeration.AccountType;
import com.asp.accountservice.enumeration.ModeOfOperation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountRequestDTO {

    @NotNull(message = "Account type is required")
    private AccountType accountType;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Branch code is required")
    private String branchCode;

    @NotNull(message = "Mode of Operation is required")
    private ModeOfOperation modeOfOperation;
}
