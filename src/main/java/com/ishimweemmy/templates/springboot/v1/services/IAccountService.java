package com.ishimweemmy.templates.springboot.v1.services;

import java.util.UUID;

import com.ishimweemmy.templates.springboot.v1.models.Account;

public interface IAccountService {
    Account getAccountById(UUID id);
    Account createAccount(Account account);
    Account updateAccount(UUID id, Account account);
    void deleteAccount(UUID id);
}