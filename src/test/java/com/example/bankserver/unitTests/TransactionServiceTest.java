package com.example.bankserver.unitTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.bankserver.customException.InsufficientBalanceException;
import com.example.bankserver.domain.dto.TransactionDTO;
import com.example.bankserver.domain.model.Account;
import com.example.bankserver.domain.model.Transaction;
import com.example.bankserver.repository.AccountRepository;
import com.example.bankserver.repository.TransactionRepository;
import com.example.bankserver.service.bankServer.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateTransaction_Successful() {
        Long senderAccountId = 1L;
        Long recipientAccountId = 2L;
        double amount = 50.0;

        Account senderAccount = new Account(senderAccountId, 100.0);
        Account recipientAccount = new Account(recipientAccountId, 200.0);

        TransactionDTO transactionDto = new TransactionDTO(1L, senderAccountId, recipientAccountId, amount, LocalDateTime.now());

        // Mock accountRepository.findById() to return sender and recipient accounts
        when(accountRepository.findById(senderAccountId)).thenReturn(Optional.of(senderAccount));
        when(accountRepository.findById(recipientAccountId)).thenReturn(Optional.of(recipientAccount));

        // Mock accountRepository.save() to return the updated accounts
        when(accountRepository.save(senderAccount)).thenReturn(senderAccount);
        when(accountRepository.save(recipientAccount)).thenReturn(recipientAccount);

        // Mock transactionRepository.save() to return the saved transaction
        Transaction savedTransaction = new Transaction(senderAccount, recipientAccount, amount, LocalDateTime.now());
        when(transactionRepository.save(any(Transaction.class))).thenReturn(savedTransaction);

        TransactionDTO result = transactionService.createTransaction(transactionDto);

        assertNotNull(result);
        assertEquals(senderAccountId, result.getSenderAccountId());
        assertEquals(recipientAccountId, result.getRecipientAccountId());
        assertEquals(amount, result.getAmount());

        // Verify that accountRepository.findById() was called twice
        verify(accountRepository, times(2)).findById(anyLong());

        // Verify that accountRepository.save() was called twice
        verify(accountRepository, times(2)).save(any(Account.class));

        // Verify that transactionRepository.save() was called once
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    public void testCreateTransaction_InsufficientBalance() {
        Long senderAccountId = 1L;
        Long recipientAccountId = 2L;
        double amount = 200.0;

        Account senderAccount = new Account(senderAccountId,100.0);
        Account recipientAccount = new Account(recipientAccountId, 200.0);

        TransactionDTO transactionDto = new TransactionDTO(1L, senderAccountId, recipientAccountId, amount, LocalDateTime.now());


        // Mock accountRepository.findById() to return sender and recipient accounts
        when(accountRepository.findById(senderAccountId)).thenReturn(Optional.of(senderAccount));
        when(accountRepository.findById(recipientAccountId)).thenReturn(Optional.of(recipientAccount));

        // Ensure that InsufficientBalanceException is thrown
        assertThrows(InsufficientBalanceException.class, () -> {
            transactionService.createTransaction(transactionDto);
        });

        // Verify that accountRepository.findById() was called twice
        verify(accountRepository, times(2)).findById(anyLong());

        // Verify that accountRepository.save() and transactionRepository.save() were never called
        verify(accountRepository, never()).save(any(Account.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    public void testCreateTransaction_AccountNotFound() {
        Long senderAccountId = 1L;
        Long recipientAccountId = 2L;
        double amount = 50.0;

        TransactionDTO transactionDto = new TransactionDTO(1L, senderAccountId, recipientAccountId, amount, LocalDateTime.now());


        // Mock accountRepository.findById() to return empty Optional (account not found)
        when(accountRepository.findById(senderAccountId)).thenReturn(Optional.empty());
        when(accountRepository.findById(recipientAccountId)).thenReturn(Optional.empty());

        // Ensure that IllegalArgumentException is thrown
        assertThrows(IllegalArgumentException.class, () -> {
            transactionService.createTransaction(transactionDto);
        });

        // Verify that accountRepository.findById() was called twice
        verify(accountRepository, times(2)).findById(anyLong());

        // Verify that accountRepository.save() and transactionRepository.save() were never called
        verify(accountRepository, never()).save(any(Account.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }
}
