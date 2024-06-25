package com.ishimweemmy.templates.springboot.v1.controllers;

import com.ishimweemmy.templates.springboot.v1.models.Account;
import com.ishimweemmy.templates.springboot.v1.models.Banking;
import com.ishimweemmy.templates.springboot.v1.services.IBankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/banking")
@RequiredArgsConstructor
public class BankingController {

    private final IBankingService bankingService;

    @PostMapping("/transaction")
    public ResponseEntity<Banking> createTransaction(@RequestBody Banking banking) {
        Banking createdTransaction = bankingService.createTransaction(banking);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }

    @PostMapping("/withdraw/{accountId}")
    public ResponseEntity<String> withdrawFromAccount(@PathVariable UUID accountId, @RequestParam double amount) {
        Account account = new Account();
        account.setId(accountId);
        bankingService.withdrawFromAccount(account, amount);
        return ResponseEntity.ok("Withdrawal successful");
    }

    @PostMapping("/deposit/{accountId}")
    public ResponseEntity<String> depositToAccount(@PathVariable UUID accountId, @RequestParam double amount) {
        Account account = new Account();
        account.setId(accountId);
        bankingService.depositToAccount(account, amount);
        return ResponseEntity.ok("Deposit successful");
    }
}
