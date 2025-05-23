package com.neptunebank.employeeservice.ENUMs;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.employeeservice.ENUMs
 * Created by: Ashish Kushwaha on 23-05-2025 13:17
 * File: s
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */


public enum BankRole {
    TELLER(BankLevel.ENTRY_LEVEL),
    CUSTOMER_SERVICE_REPRESENTATIVE(BankLevel.ENTRY_LEVEL),

    LOAN_OFFICER(BankLevel.ASSOCIATE),
    CREDIT_ANALYST(BankLevel.ASSOCIATE),
    MORTGAGE_ADVISOR(BankLevel.ASSOCIATE),

    RISK_ANALYST(BankLevel.MID_MANAGEMENT),
    COMPLIANCE_OFFICER(BankLevel.MID_MANAGEMENT),
    RELATIONSHIP_MANAGER(BankLevel.MID_MANAGEMENT),
    IT_OFFICER(BankLevel.MID_MANAGEMENT),

    OPERATIONS_MANAGER(BankLevel.SENIOR_MANAGEMENT),
    TREASURY_ANALYST(BankLevel.SENIOR_MANAGEMENT),
    SECURITY_OFFICER(BankLevel.SENIOR_MANAGEMENT),

    BRANCH_MANAGER(BankLevel.EXECUTIVE),
    AUDITOR(BankLevel.EXECUTIVE),
    INVESTMENT_BANKER(BankLevel.EXECUTIVE),
    CHIEF_FINANCIAL_OFFICER(BankLevel.EXECUTIVE);

    private final BankLevel level;

    BankRole(BankLevel level) {
        this.level = level;
    }

    public BankLevel getLevel() {
        return level;
    }
}

