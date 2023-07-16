package com.example.bankserver.unitTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.bankserver.domain.dto.AccountDTO;
import com.example.bankserver.domain.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.bankserver.repository.AccountRepository;
import com.example.bankserver.service.AccountService;

import java.util.Optional;

public class AccountServiceTest {

    private AccountService accountService;
    private AccountRepository accountRepository;

    @BeforeEach
    public void setUp() {
        accountRepository = mock(AccountRepository.class);
        accountService = new AccountService(accountRepository);
    }

    @Test
    public void testCreateAccount() {
        // Mock repository save method
        when(accountRepository.save(any())).thenReturn(new Account(1L, 100.0));

        AccountDTO accountDTO = new AccountDTO(1L, 100.0);
        AccountDTO createdAccount = accountService.createAccount(accountDTO);

        assertNotNull(createdAccount);
        assertEquals(1, createdAccount.getId());
        assertEquals(100.0, createdAccount.getBalance());
    }

    @Test
    public void testGetAccountBalance() {
        // Mock repository findById method
        Long accountId = 1L;
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(new Account(accountId, 150.0)));

        Optional<AccountDTO> accountDTO = accountService.getAccountById(accountId);

        AccountDTO account = accountDTO.get();
        assertEquals(150.0, account.getBalance());
    }
}
