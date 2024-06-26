package com.ishimweemmy.templates.springboot.v1.services;

import com.ishimweemmy.templates.springboot.v1.models.Account;
import com.ishimweemmy.templates.springboot.v1.models.Banking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface IBankingService {
    Banking withdrawFromAccount(String accountNumber, double amount);

    Banking depositToAccount(String accountNumber, double amount);

    Banking transferAmount(String fromAccountNumber, String toAccountNumber, double amount);

    void saveMessage(UUID customerId, String message);

    Page<Banking> getAll(Pageable pageable);

    Page<Banking> getTransactionsByAccountId(UUID accountId, Pageable pageable);

    Banking getById(UUID id);

    Account getAccountByAccountNumber(String accountNumber);

    boolean delete(UUID id);
}
