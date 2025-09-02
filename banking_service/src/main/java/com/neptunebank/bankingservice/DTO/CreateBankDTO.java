package com.neptunebank.bankingservice.DTO;

import com.neptunebank.bankingservice.ENUMs.RecoveryPhrases;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.bankingservice.DTO
 * Created by: Ashish Kushwaha on 02-09-2025 19:55
 * File: CreateBankDTO
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBankDTO {

    @NotBlank(message = "Username cannot be blank")
    String username;

    @NotBlank(message = "Password cannot be blank")
    String password;

    @NotBlank(message = "Recovery phrase cannot be blank")
    String newUsername;

    @NotBlank(message = "Recovery answer cannot be blank")
    String newPassword;

    @NotNull(message = "Recovery phrase cannot be blank")
    RecoveryPhrases recoveryPhrase;

    @NotBlank(message = "Recovery answer cannot be blank")
    String recoveryAnswer;
}
