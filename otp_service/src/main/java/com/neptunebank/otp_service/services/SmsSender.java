package com.neptunebank.otp_service.services;

import com.neptunebank.otp_service.models.POJO.OtpPayload;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.otp_service.services
 * Created by: Ashish Kushwaha on 24-06-2025 09:21
 * File: SmsSender
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
public class SmsSender {
    private final String DEVICE_ID = "68595138a5fdde60955c9f6d";
    private final String API_KEY = "886d2d07-1e77-483c-ac59-4778e3994135";
    
    @Async
    public void sendSms(OtpPayload otpPayload) {
        String url = "https://api.textbee.dev/api/v1/gateway/devices/" + DEVICE_ID + "/send-sms";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = Map.of(
                "recipients", List.of(otpPayload.getNumber()),
                "message", otpPayload.getMessage()
        );

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
    }
}
