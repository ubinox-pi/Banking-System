package com.neptunebank.loan_service.ENUM;

import lombok.Getter;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.loan_service.ENUM
 * Created by: Ashish Kushwaha on 14-07-2025 14:29
 * File: LoanType
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
public enum LoanType {
    PERSONAL("Personal Loan"),
    PAY_LATER("Pay Later"),
    PROPERTY("Property Loan"),
    HOME("Home Loan"),
    AUTO("Auto Loan"),
    EDUCATION("Education Loan"),
    BUSINESS("Business Loan");

    private final String description;

    LoanType(String description) {
        this.description = description;
    }

}
