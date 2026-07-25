package com.neptunebank.messaging_service.services;

import com.neptunebank.messaging_service.models.POJO.OtpPayload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.otp_service.services
 * Created by: Ashish Kushwaha on 14-08-2025 19:32
 * File: WhatsappMessages
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
@Service
public class WhatsappMessages {

    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${sms.whatsapp-link}")
    private String whatsappApiUrl;

    public ResponseEntity<?> sendOtp(OtpPayload otpPayload) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OtpPayload> entity = new HttpEntity<>(otpPayload, headers);

        ResponseEntity<?> response = restTemplate.postForEntity(whatsappApiUrl, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to send message", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
