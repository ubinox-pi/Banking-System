package com.asp.neptune_bank.DTO;

import com.asp.neptune_bank.enumeration.AccountType;
import com.asp.neptune_bank.enumeration.ModeOfOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDetailsDTO {
    private Long accountId;
    private String accountNumber;
    private BigDecimal balance;
    private AccountType accountType;
    private ModeOfOperation modeOfOperation;

    private BranchDTO branch;
}
