package com.ishimweemmy.templates.springboot.v1.serviceImpls;

import com.ishimweemmy.templates.springboot.v1.exceptions.ResourceNotFoundException;
import com.ishimweemmy.templates.springboot.v1.models.Account;
import com.ishimweemmy.templates.springboot.v1.models.Banking;
import com.ishimweemmy.templates.springboot.v1.models.Customer;
import com.ishimweemmy.templates.springboot.v1.models.Message;
import com.ishimweemmy.templates.springboot.v1.repositories.IAccountRepository;
import com.ishimweemmy.templates.springboot.v1.repositories.IBankingRepository;
import com.ishimweemmy.templates.springboot.v1.repositories.ICustomerRepository;
import com.ishimweemmy.templates.springboot.v1.repositories.IMessageRepository;
import com.ishimweemmy.templates.springboot.v1.services.IBankingService;
import com.ishimweemmy.templates.springboot.v1.standalone.MailService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BankingServiceImpl implements IBankingService {

    private final IBankingRepository bankingRepository;
    private final IAccountRepository accountRepository;
    private final ICustomerRepository customerRepository;
    private final IMessageRepository messageRepository;
    private final MailService mailService;

    @Override
    @Transactional
    public Banking createTransaction(Banking banking) {
        Account account = accountRepository.findById(banking.getAccount().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", banking.getAccount().getId()));

        switch (banking.getType()) {
            case "withdraw":
                withdrawFromAccount(account, banking.getAmount());
                break;
            case "deposit":
                depositToAccount(account, banking.getAmount());
                break;
            case "transfer-out":
                transferFromAccount(account, banking.getAmount());
                break;
            case "transfer-in":
                transferToAccount(account, banking.getAmount());
                break;
            default:
                throw new IllegalArgumentException("Invalid banking type");
        }

        accountRepository.save(account);
        banking.setBankingDateTime(LocalDateTime.now());
        return bankingRepository.save(banking);
    }

    @Transactional
    public void withdrawFromAccount(Account account, double amount) {
        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance for withdrawal");
        }
        account.setBalance(account.getBalance() - amount);
        Banking banking = new Banking(UUID.randomUUID(), account.getCustomer(), account, amount, "withdraw",
                LocalDateTime.now());
        bankingRepository.save(banking);
        saveMessage(account.getCustomer().getId(),
                "Withdrawal of " + amount + " from account " + account.getAccountNumber());
    }

    @Transactional
    public void depositToAccount(Account account, double amount) {
        account.setBalance(account.getBalance() + amount);
        Banking banking = new Banking(UUID.randomUUID(), account.getCustomer(), account, amount, "deposit",
                LocalDateTime.now());
        bankingRepository.save(banking);
        saveMessage(account.getCustomer().getId(),
                "Deposit of " + amount + " to account " + account.getAccountNumber());
    }

    @Transactional
    public void transferFromAccount(Account fromAccount, double amount) {
        if (fromAccount.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance for transfer");
        }
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        Banking banking = new Banking(UUID.randomUUID(), fromAccount.getCustomer(), fromAccount, amount, "transfer-out",
                LocalDateTime.now());
        bankingRepository.save(banking);
    }

    @Transactional
    public void transferToAccount(Account toAccount, double amount) {
        toAccount.setBalance(toAccount.getBalance() + amount);
        Banking banking = new Banking(UUID.randomUUID(), toAccount.getCustomer(), toAccount, amount, "transfer-in",
                LocalDateTime.now());
        bankingRepository.save(banking);
    }

    @Override
    public void saveMessage(UUID customerId, String message) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));
        Message msg = new Message(UUID.randomUUID(), customer, message, LocalDateTime.now());
        messageRepository.save(msg);
        mailService.sendTransactionMail(customerId.toString(), customer.getFirstName() + " " + customer.getLastName(),
                message);
    }
}
