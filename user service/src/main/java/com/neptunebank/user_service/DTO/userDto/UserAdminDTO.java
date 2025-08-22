package com.neptunebank.user_service.DTO.userDto;

import com.neptunebank.user_service.DTO.ContactDetailsDTO.ContactDetailsAdminDTO;
import com.neptunebank.user_service.DTO.NomineeDTO.NomineeAdminDTO;
import com.neptunebank.user_service.DTO.kycDTO.KycAdminDTO;
import com.neptunebank.user_service.ENUMs.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.user_service.DTO.userDto
 * Created by: Ashish Kushwaha on 26-07-2025 22:38
 * File: UserAdminDTO
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
public class UserAdminDTO {

    private Long userId;

    private String firstName;

    private String middleName;

    private String lastName;

    private LocalDate dateOfBirth;

    private Genders gender;

    private String fatherName;

    private String motherName;

    private MaritalStatus maritalStatus;

    private String spouseName;

    private Occupation occupation;

    private String salary;

    private Citizen citizen;

    private Category category;

    private Religion religion;

    private ContactDetailsAdminDTO contactDetails;

    private List<Long> accountId;

    private NomineeAdminDTO nominee;

    private Double accountInterestRate;

    private KycAdminDTO kyc;

    private Boolean isActive;

    private Boolean isBlocked;

    private Boolean isDeleted;

    private Status status;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
