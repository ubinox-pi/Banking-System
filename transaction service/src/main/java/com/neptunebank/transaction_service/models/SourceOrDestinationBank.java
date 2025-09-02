package com.neptunebank.transaction_service.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.transaction_service.models
 * Created by: Ashish Kushwaha on 25-06-2025 20:19
 * File: SourceBank
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SourceOrDestinationBank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long sourceBankId;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "sourceAccount", fetch = FetchType.EAGER)
    private Transaction transaction1;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "des", fetch = FetchType.EAGER)
    private Transaction destinationAccount;

    @Column(nullable = false)
    private String bankName;

    @Column(nullable = false)
    private String bankIFSCCode;

    @Column(nullable = false)
    private String bankBranchName;

    @Column(nullable = false)
    private String bankBranchCity;

    @Column(nullable = false)
    private String bankBranchState;

    @Column(nullable = false)
    private String bankBranchCountry;

    @Column(nullable = false)
    private String bankBranchZipCode;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "sourceOrDestinationBankId")
    private List<Transaction> transactions;

}
