package com.ishimweemmy.templates.springboot.v1.serviceImpls;

import java.util.Random;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ishimweemmy.templates.springboot.v1.exceptions.ResourceNotFoundException;
import com.ishimweemmy.templates.springboot.v1.models.Account;
import com.ishimweemmy.templates.springboot.v1.models.Customer;
import com.ishimweemmy.templates.springboot.v1.repositories.IAccountRepository;
import com.ishimweemmy.templates.springboot.v1.repositories.ICustomerRepository;
import com.ishimweemmy.templates.springboot.v1.services.IAccountService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private final IAccountRepository accountRepository;
    private final ICustomerRepository customerRepository;

    @Override
    public Account getAccountById(UUID id) {
        return accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account", "id", id));
    }

    @Override
    public Account createAccount(UUID customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));

        String accountNumber = generateAccountNumber();
        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setBalance(0);
        account.setCustomer(customer);

        return accountRepository.save(account);
    }

    @Override
    public Account updateAccount(UUID id, Account account) {
        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", id));
        existingAccount.setAccountNumber(account.getAccountNumber());
        existingAccount.setBalance(account.getBalance());
        return accountRepository.save(existingAccount);
    }

    @Override
    public void deleteAccount(UUID id) {
        accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account", "id", id));
        accountRepository.deleteById(id);
    }

    @Override
    public Page<Account> getAll(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    private String generateAccountNumber() {
        // Generate numeric account number (example: random 10-digit number)
        Random random = new Random();
        int numDigits = 10; // Specify the length of the account number
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < numDigits; i++) {
            sb.append(random.nextInt(10)); // Generate a random digit (0-9)
        }

        return sb.toString();
    }
}
