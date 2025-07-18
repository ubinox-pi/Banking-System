package com.neptunebank.loan_service.ENUM;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.loan_service.ENUM
 * Created by: Ashish Kushwaha on 14-07-2025 14:36
 * File: Status
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
public enum Status {
    PENDING("Pending"),
    APPROVED("Approved"),
    REJECTED("Rejected"),
    DISBURSED("Disbursed"),
    ACTIVE("Active"),
    COMPLETED("Completed"),
    DEFAULTED("Defaulted"),
    CLOSED("Closed");

    private final String description;

    Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
