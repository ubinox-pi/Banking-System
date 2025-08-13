package com.neptunebank.user_service.DTO.NomineeDTO;

import com.neptunebank.user_service.ENUMs.Relationship;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.user_service.DTO.NomineeDTO
 * Created by: Ashish Kushwaha on 27-07-2025 01:30
 * File: NomineeAdminDTO
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
@Builder
public class NomineeAdminDTO {

    private Long nomineeId;

    private Long user;

    private String nomineeName;

    private Relationship nomineeRelationship;

    private LocalDate nomineeDateOfBirth;

    private String nomineeMobileNumber;

    private String NomineeEmail;

    private String nomineeAadhaar;

    private String nomineePan;

    private String nomineeAddress;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
