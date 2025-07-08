package com.neptunebank.transaction_service.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.transaction_service
 * Created by: Ashish Kushwaha on 25-06-2025 20:39
 * File: SourceOrDestinationDto
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
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
