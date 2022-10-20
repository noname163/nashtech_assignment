package com.nash.assignment.controllers.admin;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nash.assignment.constant.MessEnum;
import com.nash.assignment.dto.AccountDto;
import com.nash.assignment.modal.Account;
import com.nash.assignment.modal.ResponseObject;
import com.nash.assignment.services.AccountsServiceImpl;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/admin/accounts")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AccountController {
        @Autowired
        AccountsServiceImpl accountsServiceImpl;
        @Autowired
        ModelMapper modelMapper;

        @GetMapping
        public ResponseEntity<List<AccountDto>> displayAccount() {
                List<Account> accountList = accountsServiceImpl.getAllAccounts();
                return ResponseEntity.status(HttpStatus.OK).body(
                                accountList.stream()
                                                .map(account -> modelMapper.map(account, AccountDto.class))
                                                .collect(Collectors.toList()));
        }

        @PatchMapping(value = "/active/{id}")
        public ResponseEntity<AccountDto> acctiveAccount(@PathVariable long id) {
                final int activeStatus = 1;
                AccountDto account = accountsServiceImpl.updateAccountStatus(id, activeStatus);
                return ResponseEntity.status(HttpStatus.OK).body(
                                account);
        }

        @DeleteMapping(value = "/{id}")
        public ResponseEntity<AccountDto> deleteAccount(@PathVariable long id) {
                final int deactiveStatus = 2;
                AccountDto account = accountsServiceImpl.updateAccountStatus(id, deactiveStatus);
                return ResponseEntity.status(HttpStatus.OK).body(
                                account);
        }

        @PatchMapping(value = "/update-role/{id}/{role}")
        public ResponseEntity<AccountDto> updateRole(@PathVariable long id, @PathVariable int role) {
                AccountDto account = accountsServiceImpl.updateAccountRole(id, role);
                return ResponseEntity.status(HttpStatus.OK).body(
                                account);
        }
}
