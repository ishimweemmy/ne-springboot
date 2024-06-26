package com.ishimweemmy.templates.springboot.v1.controllers;

import com.ishimweemmy.templates.springboot.v1.services.IBankingService;
import com.ishimweemmy.templates.springboot.v1.utils.Constants;
import com.ishimweemmy.templates.springboot.v1.payload.response.ApiResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/banking")
@RequiredArgsConstructor
public class BankingController {

    private final IBankingService bankingService;

    @PostMapping("/withdraw/{accountNumber}")
    public ResponseEntity<ApiResponse> withdrawFromAccount(
            @PathVariable String accountNumber, @RequestParam double amount) {
        bankingService.withdrawFromAccount(accountNumber, amount);
        return ResponseEntity.ok(ApiResponse.success("Withdrawal successful"));
    }

    @PostMapping("/deposit/{accountNumber}")
    public ResponseEntity<ApiResponse> depositToAccount(
            @PathVariable String accountNumber, @RequestParam double amount) {
        bankingService.depositToAccount(accountNumber, amount);
        return ResponseEntity.ok(ApiResponse.success("Deposit successful"));
    }

    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse> transferAmount(
            @RequestParam String fromAccountNumber,
            @RequestParam String toAccountNumber,
            @RequestParam double amount) {
        bankingService.transferAmount(fromAccountNumber, toAccountNumber, amount);
        return ResponseEntity.ok(ApiResponse.success("Transfer successful"));
    }

    @GetMapping("/transactions/{accountId}")
    public ResponseEntity<ApiResponse> getTransactionsByAccountId(
            @PathVariable UUID accountId,
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.Direction.ASC, "id");

        return ResponseEntity.ok(ApiResponse.success("Transactions fetched successfully",
                bankingService.getTransactionsByAccountId(accountId, pageable)));
    }

    @GetMapping("/transactions")
    public ResponseEntity<ApiResponse> getTransactions(
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.Direction.ASC, "id");

        return ResponseEntity.ok(ApiResponse.success("Transactions fetched successfully",
                bankingService.getAll(pageable)));
    }
}
