package com.ishimweemmy.templates.springboot.v1.controllers;

import com.ishimweemmy.templates.springboot.v1.models.Account;
import com.ishimweemmy.templates.springboot.v1.services.IAccountService;
import com.ishimweemmy.templates.springboot.v1.utils.Constants;

import jakarta.validation.Valid;

import com.ishimweemmy.templates.springboot.v1.payload.response.ApiResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

  private final IAccountService accountService;

  @PostMapping
  public ResponseEntity<ApiResponse> createAccount(@Valid @RequestParam UUID customerId) {
    Account createdAccount = accountService.createAccount(customerId);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success("Account created successfully", createdAccount));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse> getAccountById(@PathVariable UUID id) {
    Account account = accountService.getAccountById(id);
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success("Account fetched successfully", account));
  }

  @GetMapping("/all")
  public ResponseEntity<ApiResponse> getAllAccounts(
      @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
      @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success("Accounts fetched successfully", accountService.getAll(pageable)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse> deleteAccount(@PathVariable UUID id) {
    accountService.deleteAccount(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
        .body(ApiResponse.success("Account deleted successfully"));
  }
}
