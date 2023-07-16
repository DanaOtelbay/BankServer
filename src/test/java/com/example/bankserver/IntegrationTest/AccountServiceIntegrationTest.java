package com.example.bankserver.IntegrationTest;

import static org.junit.jupiter.api.Assertions.*;

import com.example.bankserver.domain.dto.AccountDTO;
import com.example.bankserver.domain.model.Account;
import com.example.bankserver.repository.AccountRepository;
import com.example.bankserver.service.bankServer.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.*;

@SpringJUnitConfig
@SpringBootTest
public class AccountServiceIntegrationTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    public void setUp() {
        // Clear the database before each test
        accountRepository.deleteAll();
    }

    @Test
    public void testCreateAccount() {
        double initialBalance = 100.0;

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setBalance(initialBalance);

        AccountDTO createdAccount = accountService.createAccount(accountDTO);

        assertNotNull(createdAccount.getId());
        assertEquals(initialBalance, createdAccount.getBalance());
    }

    @Test
    public void testGetAllAccounts() {
        // Create some accounts in the database for testing
        accountRepository.save(new Account(3L, 100.0));
        accountRepository.save(new Account(4L, 200.0));

        List<AccountDTO> accounts = accountService.getAllAccounts();

        assertEquals(2, accounts.size());
    }

    @Test
    public void testGetAccountById() {
        Long accountId = 2L;
        double accountBalance = 150.0;

        // Create an account in the database for testing
        accountRepository.save(new Account(accountId, accountBalance));

        Optional<AccountDTO> accountDtoOptional = accountService.getAccountById(accountId);

        assertTrue(accountDtoOptional.isPresent());
        AccountDTO accountDTO = accountDtoOptional.get();
        assertEquals(accountId, accountDTO.getId());
        assertEquals(accountBalance, accountDTO.getBalance());
    }
}

