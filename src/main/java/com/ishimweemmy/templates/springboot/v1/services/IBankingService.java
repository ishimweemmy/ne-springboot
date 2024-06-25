package com.ishimweemmy.templates.springboot.v1.services;

import com.ishimweemmy.templates.springboot.v1.models.Account;
import com.ishimweemmy.templates.springboot.v1.models.Banking;

import java.util.UUID;

public interface IBankingService {
    Banking createTransaction(Banking banking);

    void withdrawFromAccount(Account account, double amount);

    void depositToAccount(Account account, double amount);

    void transferFromAccount(Account fromAccount, double amount);

    void transferToAccount(Account toAccount, double amount);

    void saveMessage(UUID customerId, String message);
}