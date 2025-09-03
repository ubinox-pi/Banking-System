package com.asp.transactionservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/*
 * Copyright (c) 2025 Ayshi Shannidhya Panda. All rights reserved.
 *
 * This source code is confidential and intended solely for internal use.
 * Unauthorized copying, modification, distribution, or disclosure of this
 * file, via any medium, is strictly prohibited.
 *
 * Project: Neptune Bank
 * Author: Ayshi Shannidhya Panda
 * Created on: 02-09-2025
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
