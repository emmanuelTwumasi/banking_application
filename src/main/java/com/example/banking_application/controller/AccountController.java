package com.example.banking_application.controller;

import com.example.banking_application.dtos.*;
import com.example.banking_application.dtos.converter.AccountToAccountInfoConverter;
import com.example.banking_application.dtos.converter.TransactionToTransactionResponseConverter;
import com.example.banking_application.model.Account;
import com.example.banking_application.model.Transaction;
import com.example.banking_application.service.AccountService;
import com.example.banking_application.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/account")
public class AccountController {
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final AccountToAccountInfoConverter accountInfoMapper;
    private final TransactionToTransactionResponseConverter transactionConverter;
    @GetMapping
    public ResponseEntity<AccountDto> getAccount(@RequestBody AccountRequest accountRequest) {
        Account account = this.accountService.getAccount(accountRequest.customerId(), accountRequest.accountId());
        return new ResponseEntity<>(this.accountInfoMapper.convert(account), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AccountDto> registerAccount(@RequestBody AccountRegistrationInfo accountInfo){
        Account account = this.accountService.createAccount(accountInfo);
        return new ResponseEntity<>(this.accountInfoMapper.convert(account),HttpStatus.CREATED);
    }

    @GetMapping("{accountId}")
    public ResponseEntity<Double> getAccountBalance(@PathVariable String accountId){
        return new ResponseEntity<>(this.accountService.getAccountBalance(UUID.fromString(accountId)), HttpStatus.OK);
    }
    @PostMapping("deposit")
    public ResponseEntity<HttpStatus> deposit(@RequestBody TransactionRequestDto transaction){
        this.accountService.deposit(transaction.accountId(),transaction.amount());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("withdraw")
    public ResponseEntity<HttpStatus> withDraw(@RequestBody TransactionRequestDto transaction){
        this.accountService.withdraw(transaction.accountId(),transaction.amount());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("{accountId}/transactions")
    public ResponseEntity<List<TransactionDto>> getAccountTransactions(@PathVariable String accountId){
        List<Transaction> transactionHistory = this.transactionService.getAccountTransactionHistory(UUID.fromString(accountId));
        return new ResponseEntity<>(this.transactionConverter.convert(transactionHistory),HttpStatus.OK);
    }
}
