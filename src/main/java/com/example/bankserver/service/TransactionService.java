package com.example.bankserver.service;

import com.example.bankserver.customException.InsufficientBalanceException;
import com.example.bankserver.domain.dto.TransactionDTO;
import com.example.bankserver.domain.model.Account;
import com.example.bankserver.domain.model.Transaction;
import com.example.bankserver.repository.AccountRepository;
import com.example.bankserver.repository.TransactionRepository;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionDTO createTransaction(TransactionDTO transactionDto) {
        Long senderAccountId = transactionDto.getSenderAccountId();
        Long recipientAccountId = transactionDto.getRecipientAccountId();
        double amount = transactionDto.getAmount();

        // Retrieve sender and recipient accounts from database
        Optional<Account> senderAccountOptional = accountRepository.findById(senderAccountId);
        Optional<Account> recipientAccountOptional = accountRepository.findById(recipientAccountId);

        if (senderAccountOptional.isPresent() && recipientAccountOptional.isPresent()) {
            Account senderAccount = senderAccountOptional.get();
            Account recipientAccount = recipientAccountOptional.get();

            // Check if sender has sufficient balance
            if (senderAccount.getBalance() < amount) {
                throw new InsufficientBalanceException("Insufficient balance in the source account.");
            }

            // Update sender and recipient account balances
            senderAccount.setBalance(senderAccount.getBalance()-amount);
            recipientAccount.setBalance(recipientAccount.getBalance()+amount);

            // Save updated accounts to the database
            accountRepository.save(senderAccount);
            accountRepository.save(recipientAccount);

            // Create and save transaction to the database
            Transaction transaction = new Transaction(senderAccount, recipientAccount, amount, LocalDateTime.now());
            transactionRepository.save(transaction);
            transactionDto.setId(transaction.getId());
            transactionDto.setTransactionTime(transaction.getCreatedDateTime());
            return transactionDto;
        } else {
            throw new IllegalArgumentException("Sender account or recipient account not found");
        }
    }
}
