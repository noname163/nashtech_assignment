package com.nash.assignment.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nash.assignment.dto.AccountDto;
import com.nash.assignment.dto.AccountLoginDto;
import com.nash.assignment.services.LoginServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    LoginServiceImpl loginServiceImpl;

    @PostMapping()
    public ResponseEntity<Map<String, String>> postMethodName(@RequestBody AccountLoginDto accountDto) {

        return ResponseEntity.status(HttpStatus.OK).body(
                loginServiceImpl.login(accountDto));
    }

}
