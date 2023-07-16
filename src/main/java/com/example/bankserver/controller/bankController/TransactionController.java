package com.example.bankserver.controller.bankController;

import com.example.bankserver.domain.dto.TransactionDTO;
import com.example.bankserver.service.bankServer.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@RequestBody TransactionDTO transactionDto) {
        TransactionDTO createdTransaction = transactionService.createTransaction(transactionDto);
        log.info("transaction created: {}", createdTransaction);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }
}
