package com.neptunebank.bankingservice.repositories;

import com.neptunebank.bankingservice.models.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: Neptune
 * Package: com.neptunebank.bankingservice.repositories
 * Created by: Ashish Kushwaha on 02-09-2025 19:07
 * File: HistoryService
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
public interface HistoryRepository extends JpaRepository<LoginHistory, Long> {
}
