package com.asp.accountservice.service;

import com.asp.accountservice.DTO.AccountDetailsDTO.AccountRequestDTO;
import com.asp.accountservice.DTO.AccountDetailsDTO.AccountResponseDTO;
import com.asp.accountservice.enumeration.AccountType;
import com.asp.accountservice.mapper.AccountMapper;
import com.asp.accountservice.models.Account;
import com.asp.accountservice.models.Branch;
import com.asp.accountservice.repositories.AccountRepository;
import com.asp.accountservice.repositories.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.dao.OptimisticLockingFailureException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final BranchRepository branchRepository;

    public AccountResponseDTO createAccount(AccountRequestDTO requestDTO) {
        Branch branch = branchRepository.findByBranchCode(requestDTO.getBranchCode())
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found for code: " + requestDTO.getBranchCode()));

        Account account = Account.builder()
                .accountNumber(generateAccountNumber())
                .balance(requestDTO.getInitialBalance())
                .accountType(AccountType.valueOf(requestDTO.getAccountType()))
                .modeOfOperation(requestDTO.getModeOfOperation())
                .branch(branch)
                .build();

        Account saved = accountRepository.save(account);
        return accountMapper.toDto(saved);
    }

    private String generateAccountNumber() {
        return "SBIN" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
    }

    public AccountResponseDTO getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
        return accountMapper.toDto(account);
    }

    public List<AccountResponseDTO> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(accountMapper::toDto)
                .collect(Collectors.toList());
    }

    public AccountResponseDTO updateAccount(Long id, AccountRequestDTO requestDTO) {
        try {
            Account account = accountRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));

            accountMapper.updateAccountFromDto(requestDTO, account);
            Account updated = accountRepository.save(account);
            return accountMapper.toDto(updated);
        } catch (OptimisticLockingFailureException e) {
            throw new RuntimeException("Account was updated by another transaction. Please reload and try again.", e);
        }
    }

    public void deleteAccount(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new ResourceNotFoundException("Account not found with id: " + id);
        }
        accountRepository.deleteById(id);
    }
}
