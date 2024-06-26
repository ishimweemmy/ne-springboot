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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        public Account getAccountByAccountNumber(String accountNumber) {
                return accountRepository.findByAccountNumber(accountNumber)
                                .orElseThrow(() -> new ResourceNotFoundException("Account", "account number",
                                                accountNumber));
        }

        @Override
        @Transactional
        public Banking withdrawFromAccount(String accountNumber, double amount) {
                Account account = getAccountByAccountNumber(accountNumber);

                if (account.getBalance() < amount) {
                        throw new IllegalArgumentException("Insufficient balance for withdrawal");
                }

                account.setBalance(account.getBalance() - amount);
                Banking banking = new Banking(UUID.randomUUID(), account.getCustomer(), account, amount, "withdraw",
                                LocalDateTime.now());
                bankingRepository.save(banking);
                saveMessage(account.getCustomer().getId(),
                                "Withdrawal of " + amount + " FRW from your account " + account.getAccountNumber()
                                                + " has been completed successfully! Your new balance is "
                                                + account.getBalance() + " FRW.");

                return banking;
        }

        @Override
        @Transactional
        public Banking depositToAccount(String accountNumber, double amount) {
                Account account = getAccountByAccountNumber(accountNumber);
                System.out.println(account);
                account.setBalance(account.getBalance() + amount);
                Banking banking = new Banking(UUID.randomUUID(), account.getCustomer(), account, amount, "deposit",
                                LocalDateTime.now());
                bankingRepository.save(banking);
                saveMessage(account.getCustomer().getId(),
                                "Deposit of " + amount + " FRW to your account " + account.getAccountNumber()
                                                + " has been completed successfully! Your new balance is "
                                                + account.getBalance() + " FRW.");

                return banking;
        }

        @Override
        @Transactional
        public Banking transferAmount(String fromAccountNumber, String toAccountNumber, double amount) {
                Account fromAccount = getAccountByAccountNumber(fromAccountNumber);
                Account toAccount = getAccountByAccountNumber(toAccountNumber);

                if (fromAccount.getBalance() < amount) {
                        throw new IllegalArgumentException("Insufficient balance for transfer");
                }

                fromAccount.setBalance(fromAccount.getBalance() - amount);
                toAccount.setBalance(toAccount.getBalance() + amount);

                Banking fromBanking = new Banking(UUID.randomUUID(), fromAccount.getCustomer(), fromAccount, amount,
                                "transfer-out",
                                LocalDateTime.now());
                Banking toBanking = new Banking(UUID.randomUUID(), toAccount.getCustomer(), toAccount, amount,
                                "transfer-in",
                                LocalDateTime.now());

                bankingRepository.save(fromBanking);
                bankingRepository.save(toBanking);

                saveMessage(fromAccount.getCustomer().getId(),
                                "Transfer of " + amount + " FRW from your account " + fromAccount.getAccountNumber()
                                                + " to account " + toAccount.getAccountNumber()
                                                + " has been completed successfully! Your new balance is "
                                                + fromAccount.getBalance() + " FRW.");
                saveMessage(toAccount.getCustomer().getId(),
                                "Transfer of " + amount + " FRW to your account " + toAccount.getAccountNumber()
                                                + " from account " + fromAccount.getAccountNumber()
                                                + " has been completed successfully! Your new balance is "
                                                + toAccount.getBalance() + " FRW.");

                return fromBanking;
        }

        @Override
        public void saveMessage(UUID customerId, String message) {
                Customer customer = customerRepository.findById(customerId)
                                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));
                Message msg = new Message(UUID.randomUUID(), customer, message,
                                LocalDateTime.now());
                messageRepository.save(msg);
                mailService.sendTransactionMail(customer.getEmail(),
                                customer.getFirstName() + " " + customer.getLastName(),
                                message);
        }

        @Override
        public Banking getById(UUID id) {
                return bankingRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Banking", "id", id));
        }

        @Override
        public boolean delete(UUID id) {
                boolean exists = bankingRepository.existsById(id);
                if (!exists) {
                        throw new ResourceNotFoundException("Banking", "id", id);
                }
                bankingRepository.deleteById(id);
                return true;
        }

        @Override
        public Page<Banking> getAll(Pageable pageable) {
                return bankingRepository.findAll(pageable);
        }

        @Override
        public Page<Banking> getTransactionsByAccountId(UUID accountId, Pageable pageable) {
                return bankingRepository.findByAccountId(accountId, pageable);
        }
}
