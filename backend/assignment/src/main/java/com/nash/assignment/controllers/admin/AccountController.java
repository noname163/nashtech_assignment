package com.nash.assignment.controllers.admin;

import java.util.List;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nash.assignment.constant.RoleEnum;
import com.nash.assignment.constant.StatusEnum;
import com.nash.assignment.dto.AccountDto;
import com.nash.assignment.modal.Account;
import com.nash.assignment.services.AccountsServiceImpl;

@RestController
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
        AccountDto account = accountsServiceImpl.updateAccountStatus(id, StatusEnum.ACTIVE);
        return ResponseEntity.status(HttpStatus.OK).body(
                account);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<AccountDto> deleteAccount(@PathVariable long id) {
        AccountDto account = accountsServiceImpl.updateAccountStatus(id, StatusEnum.DEACTIVE);
        return ResponseEntity.status(HttpStatus.OK).body(
                account);
    }

    @PatchMapping(value = "/update-role/{id}")
    public ResponseEntity<AccountDto> updateRole(@PathVariable long id) {
        AccountDto account = accountsServiceImpl.updateAccountRole(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                account);
    }
}
