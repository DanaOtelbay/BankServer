package com.example.bankserver.IntegrationTest;

import static org.junit.jupiter.api.Assertions.*;

import com.example.bankserver.customException.InsufficientBalanceException;
import com.example.bankserver.domain.dto.TransactionDTO;
import com.example.bankserver.domain.model.Account;
import com.example.bankserver.repository.AccountRepository;
import com.example.bankserver.repository.TransactionRepository;
import com.example.bankserver.service.bankServer.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.util.*;

@SpringJUnitConfig
@SpringBootTest
public class TransactionServiceIntegrationTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeEach
    public void setUp() {
        // Clear the database before each test
        transactionRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    public void testCreateTransaction_Successful() {
        Long senderAccountId = 3L;
        Long recipientAccountId = 4L;
        double initialBalance = 100.0;
        double transferAmount = 50.0;

        // Create sender and recipient accounts in the database
        accountRepository.save(new Account(senderAccountId, initialBalance));
        accountRepository.save(new Account(recipientAccountId, initialBalance));

        TransactionDTO transactionDto = new TransactionDTO(1L, senderAccountId, recipientAccountId, transferAmount, LocalDateTime.now());

        TransactionDTO result = transactionService.createTransaction(transactionDto);

        assertNotNull(result.getId());
        assertNotNull(result.getTransactionTime());

        // Verify the account balances after the transaction
        Optional<Account> updatedSenderAccount = accountRepository.findById(senderAccountId);
        Optional<Account> updatedRecipientAccount = accountRepository.findById(recipientAccountId);
        assertTrue(updatedSenderAccount.isPresent());
        assertTrue(updatedRecipientAccount.isPresent());

        assertEquals(initialBalance - transferAmount, updatedSenderAccount.get().getBalance());
        assertEquals(initialBalance + transferAmount, updatedRecipientAccount.get().getBalance());
    }

    @Test
    public void testCreateTransaction_InsufficientBalance() {
        Long senderAccountId = 1L;
        Long recipientAccountId = 2L;
        double initialBalance = 30.0; // Not enough balance for the transfer
        double transferAmount = 50.0;

        // Create sender and recipient accounts in the database
        accountRepository.save(new Account(senderAccountId, initialBalance));
        accountRepository.save(new Account(recipientAccountId, initialBalance));

        TransactionDTO transactionDto = new TransactionDTO(1L, senderAccountId, recipientAccountId, transferAmount, LocalDateTime.now());

        // Ensure that InsufficientBalanceException is thrown
        assertThrows(InsufficientBalanceException.class, () -> {
            transactionService.createTransaction(transactionDto);
        });

        // Verify that the account balances are not updated
        Optional<Account> updatedSenderAccount = accountRepository.findById(senderAccountId);
        Optional<Account> updatedRecipientAccount = accountRepository.findById(recipientAccountId);
        assertTrue(updatedSenderAccount.isPresent());
        assertTrue(updatedRecipientAccount.isPresent());

        assertEquals(initialBalance, updatedSenderAccount.get().getBalance());
        assertEquals(initialBalance, updatedRecipientAccount.get().getBalance());
    }
}

