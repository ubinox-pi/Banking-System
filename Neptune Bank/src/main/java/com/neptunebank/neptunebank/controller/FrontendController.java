package com.neptunebank.neptunebank.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.neptunebank.controller
 * Created by: Ashish Kushwaha on 02-09-2025 16:48
 * File: FrontendController
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
@Controller
public class FrontendController {

    @GetMapping("/")
    public Mono<ResponseEntity<byte[]>> index() throws IOException {
        ClassPathResource indexHtml = new ClassPathResource("static/index.html");
        byte[] content = Files.readAllBytes(indexHtml.getFile().toPath());
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(content));
    }
}
