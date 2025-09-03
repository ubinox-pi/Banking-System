/*
 * Copyright (c) 2025 Ayshi Shannidhya Panda. All rights reserved.
 *
 * This source code is confidential and intended solely for internal use.
 * Unauthorized copying, modification, distribution, or disclosure of this
 * file, via any medium, is strictly prohibited.
 *
 * Project: Neptune Bank
 * Author: Ayshi Shannidhya Panda
 * Created on: 21-08-2025
 */

package com.asp.userservice.DTO.NomineeDetailsDTO;

import com.asp.userservice.enumeration.Relationship;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NomineeResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String nomineeName;
    private Relationship nomineeRelationship;
    private LocalDate nomineeDateOfBirth;
    private String nomineeMobileNumber;
    private String nomineeEmail;
    private String nomineeAadhar;
    private String nomineePan;
    private String nomineeAddress;
}

