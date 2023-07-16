package com.example.bankserver.controller.bankController;

import com.example.bankserver.domain.dto.AccountDTO;
import com.example.bankserver.service.bankServer.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
@Slf4j
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@RequestBody  AccountDTO accountDTO) {
        try{
            AccountDTO createdAccount = accountService.createAccount(accountDTO);
            log.info("Account created: {}",createdAccount);
            return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
        }catch (Exception e){
            log.error(String.valueOf(e));
        }
        return ResponseEntity.noContent().header("Content-Length", "0").build();
    }

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        List<AccountDTO> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Optional<AccountDTO>> getAccountById(@PathVariable Long accountId) {
        Optional<AccountDTO> account = accountService.getAccountById(accountId);
        return ResponseEntity.ok(account);
    }
}
