package com.example.bankserver.service.bankServer;

import com.example.bankserver.domain.dto.AccountDTO;
import com.example.bankserver.domain.model.Account;
import com.example.bankserver.repository.AccountRepository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class AccountService {
    private final AccountRepository accountRepository;

    public List<AccountDTO> getAllAccounts() {
        List<Account> accountsList = accountRepository.findAll();
        List<AccountDTO> accountDTOSList = new ArrayList<>();
        for(Account account : accountsList){
            accountDTOSList.add(convertToAccountDTO(account));
        }
        return accountDTOSList;
    }

    public AccountDTO createAccount(AccountDTO accountDTO) {
        try{
            // Convert AccountDTO to Account
            Account account = new Account();
            account.setBalance(accountDTO.getBalance());

            // Save Account and get the saved Account with generated ID
            account = accountRepository.save(account);

            // Convert Account to AccountDto
            return convertToAccountDTO(account);
        }catch (Exception e){
            log.error(String.valueOf(e));
        }
        return new AccountDTO();
    }

    public Optional<AccountDTO> getAccountById(Long accountId) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        log.info("Account exist: {}",optionalAccount.isPresent());
        return optionalAccount.map(this::convertToAccountDTO);
    }

    //Helper method to convert Account to AccountDto
    private AccountDTO convertToAccountDTO(Account account) {
        AccountDTO accountDto = new AccountDTO();
        accountDto.setId(account.getId());
        accountDto.setBalance(account.getBalance());
        return accountDto;
    }
}
