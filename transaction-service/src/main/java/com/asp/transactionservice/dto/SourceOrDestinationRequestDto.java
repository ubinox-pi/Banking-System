package com.asp.transactionservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

/*
 * Copyright (c) 2025 Ayshi Shannidhya Panda. All rights reserved.
 *
 * This source code is confidential and intended solely for internal use.
 * Unauthorized copying, modification, distribution, or disclosure of this
 * file, via any medium, is strictly prohibited.
 *
 * Project: Neptune Bank
 * Author: Ayshi Shannidhya Panda
 * Created on: 02-09-2025
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SourceOrDestinationRequestDto {
    @NotBlank(message = "Bank name cannot be blank")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Bank name must contain only letters and spaces")
    private String bankName;

    @NotBlank(message = "Bank IFSC code cannot be blank")
    private String bankIFSCCode;

    @NotBlank(message = "Bank branch name cannot be blank")
    private String bankBranchName;

    @NotBlank(message = "Bank branch city cannot be blank")
    private String bankBranchCity;

    @NotBlank(message = "Bank branch state cannot be blank")
    private String bankBranchState;

    @NotBlank(message = "Bank branch country cannot be blank")
    private String bankBranchCountry;

    @NotBlank(message = "Bank branch zip code cannot be blank")
    private String bankBranchZipCode;
}
