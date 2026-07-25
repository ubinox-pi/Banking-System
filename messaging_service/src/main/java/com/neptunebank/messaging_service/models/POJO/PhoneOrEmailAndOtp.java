package com.neptunebank.messaging_service.models.POJO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.otp_service.models.POJO
 * Created by: Ashish Kushwaha on 23-06-2025 19:47
 * File: PhoneOrEmailAndOtp
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
public class PhoneOrEmailAndOtp {

    @NotBlank(message = "Phone or Email cannot be blank")
    private String phoneOrEmail;

    @NotBlank(message = "OTP cannot be blank")
    private String otp;
}
