package com.asp.transactionservice.enumeration;

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
