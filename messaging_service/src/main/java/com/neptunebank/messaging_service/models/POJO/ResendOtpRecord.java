package com.neptunebank.messaging_service.models.POJO;

import lombok.Getter;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.otp_service.models.POJO
 * Created by: Ashish Kushwaha on 14-08-2025 20:11
 * File: ResendOtpRecord
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
public class ResendOtpRecord {
    private final Long startTime;
    private final Long endTime;
    private final String phone;

    public ResendOtpRecord(String phone) {
        this.phone = phone;
        this.startTime = System.currentTimeMillis();
        this.endTime = System.currentTimeMillis() + 1000 * 60 * 2;
    }

}
