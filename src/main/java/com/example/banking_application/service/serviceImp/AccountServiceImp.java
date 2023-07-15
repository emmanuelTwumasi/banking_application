package com.example.banking_application.service.serviceImp;

import com.example.banking_application.dtos.AccountDto;
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
    public Account createAccount(AccountDto accountInfo) {
        Customer customer = this.customerService.registerUser(accountInfo.getCustomerDetails());
        Account newAccount = new Account();
        newAccount.setAccount_type(accountInfo.getAccount_type());
        newAccount.setCustomer(customer);
        newAccount.setBalance(0.00);
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
        double initial = account.getBalance();

        Transaction transaction = new Transaction();

        try {
            account.deposit(amount);
            this.accountRepository.save(account);
            performTransaction(amount, account, initial, SUCCESSFULL, transaction, DEPOSIT);
        } catch (Exception e) {
            performTransaction(amount, account, initial, SUCCESSFULL, transaction, DEPOSIT);
            throw new RuntimeException("Transaction failed with : "+e.getMessage());
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

        if (!account.hasSufficientBalance(amount)) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        double initial = account.getBalance();
        account.withdraw(amount);
        Transaction transaction = new Transaction();

        try {
            account.withdraw(amount);
            accountRepository.save(account);
            performTransaction(amount, account, initial, SUCCESSFULL, transaction, WITHDRAWAL);

        } catch (Exception e) {
            performTransaction(amount, account, initial, FAILED, transaction, WITHDRAWAL);
            throw new RuntimeException("Transaction failed with : "+e.getMessage());
        }
    }

    @Override
    public double getAccountBalance(UUID accountNumber) {
        return getAccount(accountNumber).getBalance();
    }

    @Override
    public List<Transaction> getTransactionHistory(UUID accountNumber) {
        return getAccount(accountNumber).getTransactionHistory();
    }

}
