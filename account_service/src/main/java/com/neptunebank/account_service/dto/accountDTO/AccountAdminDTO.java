package com.neptunebank.account_service.dto.accountDTO;

import lombok.*;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.account_service.dto
 * Created by: Ashish Kushwaha on 28-07-2025 22:46
 * File: AccountAdminDTO
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
public class AccountAdminDTO {
    private Long id;

    private Long userId;

    private Long branchId;

    private String accountType;

    private String accountNumber;

    private String balance;

    private String status;

    private String modeOfOperation;

    private String accountInterestRate;

    private String createdAt;

    private String updatedAt;
}
