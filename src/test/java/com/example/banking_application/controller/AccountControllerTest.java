package com.example.banking_application.controller;

import com.example.banking_application.dtos.*;
import com.example.banking_application.dtos.converter.AccountToAccountInfoConverter;
import com.example.banking_application.dtos.converter.TransactionToTransactionResponseConverter;
import com.example.banking_application.model.Account;
import com.example.banking_application.model.Transaction;
import com.example.banking_application.repository.AccountRepository;
import com.example.banking_application.service.AccountService;
import com.example.banking_application.service.TransactionService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.*;

import static com.example.banking_application.model.enums.ACCOUNT_TYPE.SAVINGS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@WebMvcTest(AccountController.class)
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AccountService accountService;

    @MockBean
    private AccountToAccountInfoConverter accountDtoConverter;

    @MockBean
    private TransactionToTransactionResponseConverter transactionDtoConverter;

    @MockBean
    private TransactionService transactionService;

    @BeforeEach
    public void setup() {
        Account account = new Account();
        account.setId(UUID.fromString("a5de4e17-9b06-4f96-8295-6d38f14d977a"));
        account.setBalance(500.0);

        Transaction transaction = new Transaction();
        transaction.setId(UUID.fromString("c5de4e17-9b06-4f96-8295-6d38f14d977a"));
        transaction.setAmount(100.0);

        when(accountService.getAccount(account.getId())).thenReturn(account);
        when(accountService.createAccount(any(AccountRegistrationInfo.class))).thenReturn(account);
        when(accountService.getAccountBalance(account.getId())).thenReturn(account.getBalance());
        when(transactionService.getAccountTransactionHistory(account.getId())).thenReturn(Collections.singletonList(transaction));
    }

    @Test
    public void testGetAccount_ReturnsAccountDto() throws Exception {
        AccountRequest accountRequest = new AccountRequest(UUID.fromString("c5de4e17-9b06-4f96-8295-6d38f14d977a"),
                UUID.fromString("a5de4e17-9b06-4f96-8295-6d38f14d977a"));

        AccountDto account = new AccountDto(UUID.fromString("a5de4e17-9b06-4f96-8295-6d38f14d977a"), SAVINGS, "", "", "", 500.0, LocalDateTime.now());
        when(accountDtoConverter.convert(any(Account.class))).thenReturn(account);

        mockMvc.perform(get("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("a5de4e17-9b06-4f96-8295-6d38f14d977a"))
                .andExpect(jsonPath("$.balance").value(500.0));
    }

    @Test
    public void testRegisterAccount_ReturnsAccountDto() throws Exception {
        AccountRegistrationInfo accountInfo = new AccountRegistrationInfo();
        accountInfo.setAccount_type(SAVINGS);
        AccountDto account = new AccountDto(UUID.fromString("a5de4e17-9b06-4f96-8295-6d38f14d977a"), SAVINGS, "", "", "", 500.0, LocalDateTime.now());

        when(accountDtoConverter.convert(any(Account.class))).thenReturn(account);

        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountInfo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("a5de4e17-9b06-4f96-8295-6d38f14d977a"))
                .andExpect(jsonPath("$.account_type").value("SAVINGS"));
    }

    @Test
    public void testGetAccountBalance_ReturnsAccountBalance() throws Exception {
        mockMvc.perform(get("/accounts/a5de4e17-9b06-4f96-8295-6d38f14d977a"))
                .andExpect(status().isOk())
                .andExpect(content().string("500.0"));
    }

    @Test
    public void testDeposit_ReturnsHttpStatusOk() throws Exception {
        TransactionRequestDto transaction = new TransactionRequestDto(UUID.fromString("a5de4e17-9b06-4f96-8295-6d38f14d977a"), 100.0);

        mockMvc.perform(post("/accounts/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().isOk());
    }

    @Test
    public void testWithdraw_ReturnsHttpStatusOk() throws Exception {
        TransactionRequestDto transaction = new TransactionRequestDto(UUID.fromString("a5de4e17-9b06-4f96-8295-6d38f14d977a"), 50.0);

        mockMvc.perform(post("/accounts/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().isOk());
    }

//    @Test
//    public void testGetAccountTransactions_ReturnsTransactionDtoList() throws Exception {
//        when(transactionDtoConverter.convert(anyList())).thenReturn(Collections.singletonList(new TransactionDto(UUID.fromString("t5de4e17-9b06-4f96-8295-6d38f14d977a"), 100.0)));
//
//        mockMvc.perform(get("/accounts/a5de4e17-9b06-4f96-8295-6d38f14d977a/transactions"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].id").value("t5de4e17-9b06-4f96-8295-6d38f14d977a"))
//                .andExpect(jsonPath("$[0].amount").value(100.0));
//    }

    @Test
    public void testGetCustomerAccounts_ReturnsAccountDtoList() throws Exception {
        AccountDto account = new AccountDto(UUID.fromString("a5de4e17-9b06-4f96-8295-6d38f14d977a"), SAVINGS, "", "", "", 500.0, LocalDateTime.now());

        when(accountDtoConverter.convert(anyList())).thenReturn(Collections.singletonList(account));

        mockMvc.perform(get("/accounts/customer?customerId=c5de4e17-9b06-4f96-8295-6d38f14d977a"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("a5de4e17-9b06-4f96-8295-6d38f14d977a"))
                .andExpect(jsonPath("$[0].balance").value(500.0));
    }
}
