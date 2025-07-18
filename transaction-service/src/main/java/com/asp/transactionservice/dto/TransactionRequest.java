package com.asp.transactionservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRequest {

    @NotNull(message = "Sender account ID must not be null")
    private Long fromAccountId;

    @NotNull(message = "Receiver account ID must not be null")
    private Long toAccountId;

    @NotNull(message = "Amount must not be null")
    @DecimalMin(value = "1.00", inclusive = true, message = "Amount must be equal to or greater than one")
    private BigDecimal amount;

}
