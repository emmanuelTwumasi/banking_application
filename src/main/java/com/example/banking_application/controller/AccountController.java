package com.example.banking_application.controller;

import com.example.banking_application.dtos.AccountDto;
import com.example.banking_application.model.Account;
import com.example.banking_application.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/account")
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/{{customerId}}")
    public ResponseEntity<Account> getAccount(@PathVariable String customerId, @RequestBody UUID accountId) throws AuthenticationException {
      return new ResponseEntity<>(this.accountService.getAccount(UUID.fromString(customerId),accountId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Account> registerAccount(@RequestBody AccountDto accountInfo){
        return new ResponseEntity<>(this.accountService.createAccount(accountInfo),HttpStatus.CREATED);
    }
}
