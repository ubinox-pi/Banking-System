package com.asp.accountservice.mapper;

/*
 * Copyright (c) 2025 Ayshi Shannidhya Panda. All rights reserved.
 *
 * This source code is confidential and intended solely for internal use.
 * Unauthorized copying, modification, distribution, or disclosure of this
 * file, via any medium, is strictly prohibited.
 *
 * Project: Neptune Bank
 * Author: Ayshi Shannidhya Panda
 * Created on: 29-06-2025
 */

import com.asp.accountservice.DTO.AccountDetailsDTO.AccountRequestDTO;
import com.asp.accountservice.DTO.AccountDetailsDTO.AccountResponseDTO;
import com.asp.accountservice.models.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(source = "initialBalance", target = "balance")
    @Mapping(target = "accountId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "branch", ignore = true) // set manually in service
    Account toEntity(AccountRequestDTO dto);

    @Mapping(source = "branch.branchCode", target = "branchCode")
    @Mapping(source = "branch.branchId", target = "branchId")
    AccountResponseDTO toDto(Account account);

    @Mapping(source = "initialBalance", target = "balance")
    @Mapping(target = "accountId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "branch", ignore = true)
    void updateAccountFromDto(AccountRequestDTO dto, @MappingTarget Account account);
}
