package com.neptunebank.bankingservice.DTO;

import com.neptunebank.bankingservice.ENUMs.RecoveryPhrases;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.bankingservice.DTO
 * Created by: Ashish Kushwaha on 25-08-2025 21:27
 * File: LoginDTO
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
@Data
public class LoginDTO {

    @NotBlank(message = "Username cannot be blank")
    @Pattern(regexp = "^[aA-zZ0-9_]{5,20}$", message = "Username must be 5-20 characters long and can contain letters, numbers, and underscores only")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one number, and one special character")
    private String password;

    @NotNull(message = "Recovery phrase cannot be blank")
    private RecoveryPhrases recoveryPhrase;

    @NotBlank(message = "Recovery answer cannot be blank")
    private String recoveryAnswer;

    @NotBlank(message = "Mode cannot be blank")
    private String mode;
}
