package com.example.banking_application.service;

import com.example.banking_application.dtos.AccountRegistrationInfo;
import com.example.banking_application.exceptions.ResourceNotFoundException;
import com.example.banking_application.model.Account;
import com.example.banking_application.model.Customer;
import com.example.banking_application.repository.AccountRepository;
import com.example.banking_application.service.serviceImp.AccountServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static com.example.banking_application.model.enums.ACCOUNT_TYPE.SAVINGS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerService customerService;
    @Mock
    private TransactionService transactionService;
    private AccountService accountService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        accountService = new AccountServiceImp(accountRepository, customerService, transactionService);
    }

    @Test
    public void testCreateAccount() {
        UUID customerId = UUID.randomUUID();
        AccountRegistrationInfo accountInfo = new AccountRegistrationInfo();
        accountInfo.setAccount_type(SAVINGS);
        accountInfo.setCustomerId(customerId);

        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setUsername("John Doe");

        Account newAccount = new Account();
        newAccount.setId(UUID.randomUUID());
        newAccount.setAccount_type(accountInfo.getAccount_type());
        newAccount.setCustomer(customer);

        when(customerService.verifyCustomer(customerId)).thenReturn(customer);
        when(accountRepository.save(any(Account.class))).thenReturn(newAccount);

        Account createdAccount = accountService.createAccount(accountInfo);

        assertEquals(newAccount.getId(), createdAccount.getId());
        assertEquals(accountInfo.getAccount_type(), createdAccount.getAccount_type());
        assertEquals(customer, createdAccount.getCustomer());
    }

    @Test
    public void testGetAccount_WithValidCustomerIdAndAccountId_ReturnsAccount() {
        UUID customerId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();

        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setUsername("John Doe");

        Account account = new Account();
        account.setId(accountId);
        account.setAccount_type(SAVINGS);
        account.setCustomer(customer);

        when(customerService.verifyCustomer(customerId)).thenReturn(customer);
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        Account retrievedAccount = accountService.getAccount(customerId, accountId);

        assertNotNull(retrievedAccount);
        assertEquals(accountId, retrievedAccount.getId());
        assertEquals(SAVINGS, retrievedAccount.getAccount_type());
        assertEquals(customer, retrievedAccount.getCustomer());
    }

    @Test
    public void testGetAccount_WithInvalidCustomerId_ThrowsAccountNotFoundException() {
        UUID customerId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();

        when(customerService.verifyCustomer(customerId))
                .thenThrow(new ResourceNotFoundException("Account not found"));

        assertThrows(ResourceNotFoundException.class, () -> {
            accountService.getAccount(customerId, accountId);
        });
    }

    @Test
    public void testGetAccount_WithInvalidAccountId_ThrowsAccountNotFoundException() {
        UUID customerId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();

        when(customerService.verifyCustomer(customerId)).thenReturn(new Customer());
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            accountService.getAccount(customerId, accountId);
        });
    }

    @Test
    public void testGetAccount_WithInvalidCustomerForAccount_ThrowsRuntimeException() {
        UUID customerId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();

        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());

        Account account = new Account();
        account.setId(accountId);
        account.setCustomer(customer);

        when(customerService.verifyCustomer(customerId)).thenReturn(new Customer());

        assertThrows(RuntimeException.class, () -> {
            accountService.getAccount(customerId, accountId);
        });
    }

    @Test
    public void testDeposit_WithValidAccountNumber_PerformsDepositAndTransaction() {
        UUID accountNumber = UUID.randomUUID();
        double amount = 100.0;

        Account account = new Account();
        account.setId(accountNumber);
        account.setBalance(0.0);

        when(accountRepository.findById(accountNumber)).thenReturn(Optional.of(account));

        accountService.deposit(accountNumber, amount);

        assertEquals(100.0, account.getBalance(), 0.001);

    }

    @Test
    public void testDeposit_WithInvalidAccountNumber_ThrowsAccountNotFoundException() {
        UUID accountNumber = UUID.randomUUID();
        double amount = 100.0;

        when(accountRepository.findById(accountNumber)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            accountService.deposit(accountNumber, amount);
        });
    }

    @Test
    public void testWithdraw_WithValidAccountNumberAndSufficientBalance_PerformsWithdrawalAndTransaction() {
        UUID accountNumber = UUID.randomUUID();
        double amount = 50.0;

        Account account = new Account();
        account.setId(accountNumber);
        account.setBalance(100.0);

        when(accountRepository.findById(accountNumber)).thenReturn(Optional.of(account));

        accountService.withdraw(accountNumber, amount);

        assertEquals(50.0, account.getBalance(), 0.001);

    }

    @Test
    public void testWithdraw_WithValidAccountNumberAndInsufficientBalance_ThrowsIllegalArgumentException() {
        UUID accountNumber = UUID.randomUUID();
        double amount = 100.0;

        Account account = new Account();
        account.setId(accountNumber);
        account.setBalance(50.0);

        when(accountRepository.findById(accountNumber)).thenReturn(Optional.of(account));

        assertThrows(RuntimeException.class, () -> {
            accountService.withdraw(accountNumber, amount);
        });
    }

    @Test
    public void testWithdraw_WithInvalidAccountNumber_ThrowsAccountNotFoundException() {
        UUID accountNumber = UUID.randomUUID();
        double amount = 100.0;

        when(accountRepository.findById(accountNumber)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            accountService.withdraw(accountNumber, amount);
        });
    }

    @Test
    public void testGetAccountBalance_WithValidAccountNumber_ReturnsAccountBalance() {
        UUID accountNumber = UUID.randomUUID();

        Account account = new Account();
        account.setId(accountNumber);
        account.setBalance(200.0);

        when(accountRepository.findById(accountNumber)).thenReturn(Optional.of(account));

        double balance = accountService.getAccountBalance(accountNumber);

        assertEquals(200.0, balance, 0.001);
    }

    @Test
    public void testGetAccountBalance_WithInvalidAccountNumber_ThrowsAccountNotFoundException() {
        UUID accountNumber = UUID.randomUUID();

        when(accountRepository.findById(accountNumber)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            accountService.getAccountBalance(accountNumber);
        });
    }

}