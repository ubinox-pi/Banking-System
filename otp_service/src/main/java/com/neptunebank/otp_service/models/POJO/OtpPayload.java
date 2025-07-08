package com.neptunebank.otp_service.models.POJO;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.user_service.models.POJO
 * Created by: Ashish Kushwaha on 16-06-2025 10:18
 * File: OtpPayload
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OtpPayload {
    private String number;
    private String message;

    public OtpPayload(String number, String message) {
        this.number = number;
        this.message = message;
    }

}
