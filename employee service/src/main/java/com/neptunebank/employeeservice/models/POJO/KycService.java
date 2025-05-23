package com.neptunebank.employeeservice.models.POJO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.employeeservice.models.POJO
 * Created by: Ashish Kushwaha on 23-05-2025 19:26
 * File: a
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
public class KycService {
    @NotBlank(message = "Kyc Id is required.")
    private Long kycId;
    @NotBlank(message = "Employee Id is required.")
    private Long employeeId;
}
