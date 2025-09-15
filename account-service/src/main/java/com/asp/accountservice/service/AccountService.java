package com.asp.accountservice.service;

import com.asp.accountservice.DTO.AccountDetailsDTO.AccountRequestDTO;
import com.asp.accountservice.DTO.AccountDetailsDTO.AccountResponseDTO;
import com.asp.accountservice.DTO.AccountDetailsDTO.AccountUpdateRequestDTO;
import com.asp.accountservice.DTO.BranchDTO.BranchResponseDTO;
import com.asp.accountservice.client.UserValidationClient;
import com.asp.accountservice.models.Account;
import com.asp.accountservice.models.Branch;
import com.asp.accountservice.repositories.AccountRepository;
import com.asp.accountservice.repositories.BranchRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final UserValidationClient userValidationClient;
    private final AccountRepository accountRepository;
    private final BranchRepository branchRepository;

    public AccountService(UserValidationClient userValidationClient,
                          AccountRepository accountRepository,
                          BranchRepository branchRepository) {
        this.userValidationClient = userValidationClient;
        this.accountRepository = accountRepository;
        this.branchRepository = branchRepository;
    }

    public AccountResponseDTO createAccount(AccountRequestDTO requestDTO) {
        Long userId = requestDTO.getUserId();

        if (userId == null) {
            return AccountResponseDTO.builder()
                    .success(false)
                    .message("User ID is required.")
                    .build();
        }

        if (!userValidationClient.isUserExist(userId)) {
            return AccountResponseDTO.builder()
                    .success(false)
                    .message("User not found with ID: " + userId)
                    .build();
        }

        Branch branch = null;
        if (requestDTO.getBranchCode() != null) {
            var byCode = branchRepository.findByBranchCode(requestDTO.getBranchCode());
            if (byCode.isEmpty()) {
                return AccountResponseDTO.builder()
                        .success(false)
                        .message("Branch not found with code: " + requestDTO.getBranchCode())
                        .build();
            }
            branch = byCode.get();
        } else {
            return AccountResponseDTO.builder()
                    .success(false)
                    .message("Branch code is required.")
                    .build();
        }

        String accountNumber = generateAccountNumber();

        Account account = Account.builder()
                .accountNumber(accountNumber)
                .userId(userId)
                .branch(branch)
                .modeOfOperation(requestDTO.getModeOfOperation())
                .accountType(requestDTO.getAccountType())
                .build();

        Account saved = accountRepository.save(account);

        return AccountResponseDTO.builder()
                .accountNumber(accountNumber)
                .accountId(saved.getAccountId())
                .accountNumber(saved.getAccountNumber())
                .accountType(saved.getAccountType())
                .balance(saved.getBalance())
                .userId(saved.getUserId())
                .modeOfOperation(saved.getModeOfOperation())
                .branchId(saved.getBranch() != null ? saved.getBranch().getBranchId() : null)
                .branchCode(saved.getBranch() != null ? saved.getBranch().getBranchCode() : null)
                .createdAt(saved.getCreatedAt())
                .success(true)
                .message("Account created successfully for user " + userId)
                .build();
    }

    public String generateAccountNumber() {
        String candidate;
        int attempts = 0;
        do {
            candidate = "NB" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
            attempts++;
            if (attempts > 20) {
                throw new IllegalStateException("Unable to generate unique account number after " + attempts + " attempts");
            }

        } while (accountRepository.existsByAccountNumber(candidate));

        return candidate;
    }


    public AccountResponseDTO getAccountById(Long id) {
        Account account = accountRepository.getAccountByAccountId(id);

        if (id == null) {
            return AccountResponseDTO.builder()
                    .success(false)
                    .message("Account ID required.")
                    .build();
        }

        if (account == null) {
            return AccountResponseDTO.builder()
                    .success(false)
                    .message("Account not found with ID: " + id)
                    .build();
        }

        return AccountResponseDTO.builder()
                .success(true)
                .message("Account found")
                .accountId(account.getAccountId())
                .accountNumber(account.getAccountNumber())
                .userId(account.getUserId())
                .balance(account.getBalance())
                .accountType(account.getAccountType())
                .modeOfOperation(account.getModeOfOperation())
                .branchId(account.getBranch() != null ? account.getBranch().getBranchId() : null)
                .branchCode(account.getBranch() != null ? account.getBranch().getBranchCode() : null)
                .createdAt(account.getCreatedAt())
//                .branch(null)
                .build();
    }

    public List<AccountResponseDTO> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(this::maptoDTO)
                .collect(Collectors.toList());
    }

    private AccountResponseDTO maptoDTO(Account account) {
        BranchResponseDTO branchResponseDTO = null;
        Long branchId = null;
        String branchCode = null;
        if (account.getBranch() != null) {
            branchResponseDTO = BranchResponseDTO.builder()
                    .branchId(account.getBranch().getBranchId())
                    .branchCode(account.getBranch().getBranchCode())
                    .branchName(account.getBranch().getBranchName())
                    .branchAddress(account.getBranch().getBranchAddress())
                    .branchCity(account.getBranch().getBranchCity())
                    .branchState(account.getBranch().getBranchState())
                    .branchZip(account.getBranch().getBranchZip())
                    .success(true)
                    .message("Branch Found.")
                    .build();
            branchId = account.getBranch().getBranchId();
            branchCode = account.getBranch().getBranchCode();
        }
        return AccountResponseDTO.builder()
                .accountId(account.getAccountId())
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .balance(account.getBalance())
                .userId(account.getUserId())
                .modeOfOperation(account.getModeOfOperation())
                .branchCode(branchCode)
                .branchId(branchId)
                .createdAt(account.getCreatedAt())
                .branch(branchResponseDTO)
                .success(true)
                .message("Account Found.")
                .build();
    }

    public void deleteAccount(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new EntityNotFoundException("Account not found: " + id);
        }
        accountRepository.deleteByAccountId(id); // runs inside transaction due to annotation
    }

//    public AccountResponseDTO updateAccount(Long id, @Valid AccountUpdateRequestDTO updatedAccount) {
//        if (id == null) {
//            return AccountResponseDTO.builder()
//                    .success(false)
//                    .message("Account ID required.")
//                    .build();
//        }
//
//        var accountId = accountRepository.findById(id);
//        if (accountId.isEmpty()) {
//            return AccountResponseDTO.builder()
//                    .success(false)
//                    .message("Account not found with ID: " + id)
//                    .build();
//        }
//        Account account = accountId.get();
//
//        if (updatedAccount.getUserId() != null && !updatedAccount.getUserId().equals(account.getUserId())) {
//            Long newUserId = updatedAccount.getUserId();
//            if (!userValidationClient.isUserExist(newUserId)) {
//                return AccountResponseDTO.builder()
//                        .success(false)
//                        .message("User not found with ID: " + newUserId)
//                        .build();
//            }
//            account.setUserId(newUserId);
//        }
//        if (updatedAccount.getBranchCode() != null) {
//            var byCode = branchRepository.findByBranchCode(updatedAccount.getBranchCode());
//            if (byCode.isEmpty()) {
//                return AccountResponseDTO.builder()
//                        .success(false)
//                        .message("Branch not found with code: " + updatedAccount.getBranchCode())
//                        .build();
//            }
//            account.setBranch(byCode.get());
//        }
//        if (updatedAccount.getAccountType() != null) {
//            account.setAccountType(updatedAccount.getAccountType());
//        }
//        if (updatedAccount.getModeOfOperation() != null) {
//            account.setModeOfOperation(updatedAccount.getModeOfOperation());
//        }
//        if (updatedAccount.getBalance() != null) {
//            account.setBalance(updatedAccount.getBalance());
//        }
//
//        Account saved = accountRepository.save(account);
//
//        return AccountResponseDTO.builder()
//                .accountId(saved.getAccountId())
//                .accountNumber(saved.getAccountNumber())
//                .accountType(saved.getAccountType())
//                .balance(saved.getBalance())
//                .userId(saved.getUserId())
//                .modeOfOperation(saved.getModeOfOperation())
//                .branchId(saved.getBranch() != null ? saved.getBranch().getBranchId() : null)
//                .branchCode(saved.getBranch() != null ? saved.getBranch().getBranchCode() : null)
//                .updatedAt(saved.getUpdatedAt())
//                .balance(saved.getBalance())
//                .success(true)
//                .message("Account updated successfully for ID " + id)
//                .build();
//    }

    public AccountResponseDTO updateAccount(Long id,
                                            AccountUpdateRequestDTO updatedAccount,
                                            Long requesterId,
                                            String requesterUsername,
                                            boolean isAdmin) {
        // Basic validation
        if (id == null) {
            return AccountResponseDTO.builder()
                    .success(false)
                    .message("Account ID required.")
                    .build();
        }

        Optional<Account> accountOpt = accountRepository.findById(id);
        if (accountOpt.isEmpty()) {
            return AccountResponseDTO.builder()
                    .success(false)
                    .message("Account not found with ID: " + id)
                    .build();
        }
        Account account = accountOpt.get();

        boolean isOwner = account.getUserId() != null && account.getUserId().equals(requesterId);
        if (!isAdmin && !isOwner) {
            // Not allowed to update someone else's account
            return AccountResponseDTO.builder()
                    .success(false)
                    .message("Access denied: not account owner or admin.")
                    .build();
        }

        // -- Handle userId change: only admin allowed --
        if (updatedAccount.getUserId() != null && !updatedAccount.getUserId().equals(account.getUserId())) {
            if (!isAdmin) {
                return AccountResponseDTO.builder()
                        .success(false)
                        .message("Only admin can change account owner (userId).")
                        .build();
            }
            Long newUserId = updatedAccount.getUserId();
            // Validate user exists via user validation client
            if (!userValidationClient.isUserExist(newUserId)) {
                return AccountResponseDTO.builder()
                        .success(false)
                        .message("User not found with ID: " + newUserId)
                        .build();
            }
            account.setUserId(newUserId);
        }

        // -- Handle branch change: only admin --
        if (updatedAccount.getBranchCode() != null) {
            if (!isAdmin) {
                return AccountResponseDTO.builder()
                        .success(false)
                        .message("Only admin can change branch.")
                        .build();
            }
            var byCode = branchRepository.findByBranchCode(updatedAccount.getBranchCode());
            if (byCode.isEmpty()) {
                return AccountResponseDTO.builder()
                        .success(false)
                        .message("Branch not found with code: " + updatedAccount.getBranchCode())
                        .build();
            }
            account.setBranch(byCode.get());
        }

        // -- accountType: only admin --
        if (updatedAccount.getAccountType() != null) {
            if (!isAdmin) {
                return AccountResponseDTO.builder()
                        .success(false)
                        .message("Only admin can change account type.")
                        .build();
            }
            account.setAccountType(updatedAccount.getAccountType());
        }

        // -- modeOfOperation: owner or admin allowed --
        if (updatedAccount.getModeOfOperation() != null) {
            account.setModeOfOperation(updatedAccount.getModeOfOperation());
        }

        // -- balance: only admin (prevent users from setting balances) --
        if (updatedAccount.getBalance() != null) {
            if (!isAdmin) {
                return AccountResponseDTO.builder()
                        .success(false)
                        .message("Only admin can change balance.")
                        .build();
            }
            account.setBalance(updatedAccount.getBalance());
        }

        // Save and return DTO
        Account saved = accountRepository.save(account);

        // Optional: record audit log (not implemented here)
        // auditService.recordAccountUpdate(requesterId, requesterUsername, id, updatedAccount);

        return AccountResponseDTO.builder()
                .accountId(saved.getAccountId())
                .accountNumber(saved.getAccountNumber())
                .accountType(saved.getAccountType())
                .balance(saved.getBalance())
                .userId(saved.getUserId())
                .modeOfOperation(saved.getModeOfOperation())
                .branchId(saved.getBranch() != null ? saved.getBranch().getBranchId() : null)
                .branchCode(saved.getBranch() != null ? saved.getBranch().getBranchCode() : null)
                .updatedAt(saved.getUpdatedAt())
                .success(true)
                .message("Account updated successfully for ID " + id)
                .build();
    }
}
