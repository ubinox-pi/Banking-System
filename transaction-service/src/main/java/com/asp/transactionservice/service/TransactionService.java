package com.asp.transactionservice.service;

import com.asp.transactionservice.dto.TransactionRequest;
import com.asp.transactionservice.dto.TransactionResponse;
import com.asp.transactionservice.enumeration.TransactionStatus;
import com.asp.transactionservice.mapper.TransactionMapper;
import com.asp.transactionservice.model.Transaction;
import com.asp.transactionservice.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public TransactionResponse createTransaction(TransactionRequest request) {

        if (request.getFromAccountId().equals(request.getToAccountId())) {
            throw new ValidationException("Cannot transfer to the same account.");
        }

        if (request.getAmount() == null || request.getAmount().signum() <= 0) {
            throw new ValidationException("Transfer amount must be greater than zero.");
        }


        Transaction transaction = transactionMapper.toEntity(request);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setStatus(TransactionStatus.SUCCESS);

        Transaction saved = transactionRepository.save(transaction);
        return transactionMapper.toResponse(saved);
    }

    public List<TransactionResponse> getTransactionsByAccountId(Long accountId) {
        List<Transaction> transactions = transactionRepository.findByFromAccountIdOrToAccountId(accountId, accountId);
        return transactions.stream()
                .map(transactionMapper::toResponse)
                .toList();
    }

    public TransactionResponse getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found with ID: " + id));
        return transactionMapper.toResponse(transaction);
    }
}
