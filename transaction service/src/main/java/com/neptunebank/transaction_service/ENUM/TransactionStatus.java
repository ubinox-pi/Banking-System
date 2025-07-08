package com.neptunebank.transaction_service.ENUM;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.transaction_service.ENUM
 * Created by: Ashish Kushwaha on 25-06-2025 20:50
 * File: TransactionStatus
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
public enum TransactionStatus {
    PENDING,
    COMPLETED,
    FAILED,
    CANCELLED,
    REFUNDED,
    IN_PROGRESS,
    ON_HOLD,
    REVERSED,
    DECLINED,
    AWAITING_CONFIRMATION,
    PARTIALLY_COMPLETED,
    UNDER_REVIEW,
    EXPIRED,
    PROCESSING_ERROR,
    MANUAL_INTERVENTION_REQUIRED,
    SCHEDULED,
    BLOCKED,
    UNCONFIRMED,
    SETTLED,
    AUTHORIZED,
    DECLINED_BY_BANK,
    FRAUD_SUSPECTED,
    DUPLICATE,
    NOT_PROCESSED,
    UNKNOWN_ERROR,
    SUCCESSFUL,
    FAILED_INSUFFICIENT_FUNDS,
    FAILED_ACCOUNT_CLOSED,
    FAILED_INVALID_DETAILS,
    FAILED_TECHNICAL_ERROR,
    FAILED_LIMIT_EXCEEDED,
    FAILED_SUSPICIOUS_ACTIVITY,
    FAILED_BANK_UNAVAILABLE,
    FAILED_UNAUTHORIZED_ACCESS,
    FAILED_COMPLIANCE_ISSUE,
    FAILED_OTHER_REASON,
    SUCCESS_PENDING_VERIFICATION,
}
