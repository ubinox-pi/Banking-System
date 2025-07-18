package com.asp.accountservice.DTO.AccountDetailsDTO;

import com.asp.accountservice.enumeration.ModeOfOperation;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountRequestDTO {

    @NotBlank(message = "Account type is required")
    private String accountType;

    @NotBlank(message = "Account Number is required")
    private String accountNumber;


    @NotNull(message = "Initial balance is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Balance cannot be negative")
    private BigDecimal initialBalance;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Branch code is required")
    private String branchCode;

    @NotNull(message = "Mode of Operation is required")
    private ModeOfOperation modeOfOperation;
}
