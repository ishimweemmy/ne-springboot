package com.ishimweemmy.templates.springboot.v1.serviceImpls;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ishimweemmy.templates.springboot.v1.exceptions.ResourceNotFoundException;
import com.ishimweemmy.templates.springboot.v1.models.Account;
import com.ishimweemmy.templates.springboot.v1.repositories.IAccountRepository;
import com.ishimweemmy.templates.springboot.v1.services.IAccountService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private final IAccountRepository accountRepository;

    @Override
    public Account getAccountById(UUID id) {
        return accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account", "id", id));
    }

    @Override
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account updateAccount(UUID id, Account account) {
        Account existingAccount = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account", "id", id));
        existingAccount.setAccountNumber(account.getAccountNumber());
        existingAccount.setBalance(account.getBalance());
        return accountRepository.save(existingAccount);
    }

    @Override
    public void deleteAccount(UUID id) {
        accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account", "id", id));
        accountRepository.deleteById(id);
    }
}
