package com.nash.assignment.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nash.assignment.dto.AccountDto;
import com.nash.assignment.services.AccountsServiceImpl;

@RestController
@RequestMapping("/register")
public class RegisterAccountController {
    @Autowired
    AccountsServiceImpl accountsServiceImpl;

    @PostMapping
    public ResponseEntity<AccountDto> insertAccount(@Valid @RequestBody AccountDto accountDto) {
        AccountDto account = accountsServiceImpl.insertAccounts(accountDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                account);
    }

}
