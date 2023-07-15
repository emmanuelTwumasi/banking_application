package com.example.banking_application.service.serviceImp;

import com.example.banking_application.dtos.AccountRegistrationInfo;
import com.example.banking_application.exceptions.AccountNotFoundException;
import com.example.banking_application.model.Account;
import com.example.banking_application.model.Customer;
import com.example.banking_application.model.Transaction;
import com.example.banking_application.model.enums.TRANSACTION_STATUS;
import com.example.banking_application.model.enums.TRANSACTION_TYPE;
import com.example.banking_application.repository.AccountRepository;
import com.example.banking_application.service.AccountService;
import com.example.banking_application.service.CustomerService;
import com.example.banking_application.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.example.banking_application.model.enums.TRANSACTION_STATUS.FAILED;
import static com.example.banking_application.model.enums.TRANSACTION_STATUS.SUCCESSFULL;
import static com.example.banking_application.model.enums.TRANSACTION_TYPE.DEPOSIT;
import static com.example.banking_application.model.enums.TRANSACTION_TYPE.WITHDRAWAL;

@RequiredArgsConstructor
@Service
public class AccountServiceImp implements AccountService {
    private final AccountRepository accountRepository;
    private final CustomerService customerService;
    private final TransactionService transactionService;

    @Override
    public Account createAccount(AccountRegistrationInfo accountInfo) {
        Customer customer = this.customerService.verifyCustomer(accountInfo.getCustomerId());
        Account newAccount = new Account();
        newAccount.setAccount_type(accountInfo.getAccount_type());
        newAccount.setCustomer(customer);
        return accountRepository.save(newAccount);
    }

    @Override
    public Account getAccount(UUID customerId, UUID accountId) {
        customerService.verifyCustomer(customerId);
        Account account = accountRepository.findById(accountId).orElseThrow(() ->
                new AccountNotFoundException("Account not found with number :" + accountId));
        if (customerId != account.getCustomer().getId()) {
            throw new RuntimeException("Can't access this account");
        }
        return account;
    }

    @Override
    public Account getAccount(UUID accountId) {
        return accountRepository.findById(accountId).orElseThrow(() ->
                new AccountNotFoundException("Account not found with number :" + accountId));
    }

    @Override
    public Account updateAccount(Account account) {
        if (account == null || account.getId() == null || account.getCustomer() == null) {
            return null;
        }

        Account existingAccount = this.getAccount(account.getId(), account.getCustomer().getId());

        existingAccount.setBalance(account.getBalance());
        if (account.getCustomer() != null) {
            existingAccount.setCustomer(account.getCustomer());
        }

        if (account.getAccount_type() != null) {
            existingAccount.setAccount_type(account.getAccount_type());
        }

        return this.accountRepository.save(existingAccount);
    }

    @Override
    public void deposit(UUID accountNumber, double amount) {
        Account account = this.getAccount(accountNumber);
        double initialBalance = account.getBalance();
        Transaction transaction = new Transaction();

        try {
            account.deposit(amount);
            this.accountRepository.save(account);
            this.performTransaction(amount, account, initialBalance, SUCCESSFULL, transaction, DEPOSIT);
        } catch (Exception e) {
            this.performTransaction(amount, account, initialBalance, SUCCESSFULL, transaction, DEPOSIT);
            throw new RuntimeException("Transaction failed : "+e.getMessage());
        }
    }

    private void performTransaction(double amount, Account account,
                                    double initialAmount, TRANSACTION_STATUS transactionStatus,
                                    Transaction transaction, TRANSACTION_TYPE transactionType) {
        transaction.setType(transactionType);
        transaction.setInitialBalance(initialAmount);
        transaction.setAmount(amount);
        transaction.setCurrentBalance(account.getBalance());
        transaction.setStatus(transactionStatus);
        transaction.setAccount(account);
        this.transactionService.addTransaction(transaction);
    }

    @Override
    public void withdraw(UUID accountNumber, double amount) {
        Account account = getAccount(accountNumber);

        if (account == null) {
            throw new IllegalArgumentException("Account not found");
        }

        double initialBalance = account.getBalance();
        Transaction transaction = new Transaction();

        try {
            account.withdraw(amount);
            accountRepository.save(account);
            this.performTransaction(amount, account, initialBalance, SUCCESSFULL, transaction, WITHDRAWAL);
        } catch (Exception e) {
            this.performTransaction(amount, account, initialBalance, FAILED, transaction, WITHDRAWAL);
            throw new RuntimeException("Transaction failed : "+e.getMessage());
        }
    }

    @Override
    public double getAccountBalance(UUID accountNumber) {
        return this.getAccount(accountNumber).getBalance();
    }

    @Override
    public List<Transaction> getTransactionHistory(UUID accountNumber) {
        return this.getAccount(accountNumber).getTransactionHistory();
    }

}
