package com.ishimweemmy.templates.springboot.v1.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ishimweemmy.templates.springboot.v1.models.Account;

public interface IAccountService {
    Account getAccountById(UUID id);

    Account createAccount(UUID customerId);

    Account updateAccount(UUID id, Account account);

    void deleteAccount(UUID id);

    Page<Account> getAll(Pageable pageable);
}