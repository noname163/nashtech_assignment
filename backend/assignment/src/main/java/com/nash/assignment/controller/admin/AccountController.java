package com.nash.assignment.controller.admin;

import java.util.Optional;

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

import com.nash.assignment.constant_variable.MessEnum;
import com.nash.assignment.modal.Accounts;
import com.nash.assignment.modal.ResponseObject;
import com.nash.assignment.service.AccountsServiceImpl;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AccountController {
    @Autowired
    AccountsServiceImpl accountsServiceImpl;

    @GetMapping(value = "/displayAccounts")
    public ResponseEntity<ResponseObject> displayAccount() {
        Iterable<Accounts> accountList = accountsServiceImpl.getAllAccounts();
        return accountList != null ? ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("200", MessEnum.FOUND.name(), "DATA FOUND", accountList))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("500", MessEnum.NOT_FOUND.name(), "DATA NOT FOUND", accountList));
    }

    @PatchMapping(value = "/activeAccount/{id}")
    public ResponseEntity<ResponseObject> acctiveAccount(@PathVariable long id) {
        Optional<Accounts> accountValue = accountsServiceImpl.getAccountById(id);
        String statusValue = accountValue.get().getStatus().getStatus();
        final int activeStatus = 1;
        Accounts account = accountsServiceImpl.updateAccountStatus(accountValue.get(), activeStatus);
        return statusValue != account.getStatus().getStatus() ? ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("200", MessEnum.SUCCESS.name(), "ACTIVE ACCOUNT SUCCESS", account))
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ResponseObject("500", MessEnum.UNSUCCESS.name(), "ACTIVE ACCOUNT FAIL", account));
    }

    @DeleteMapping(value = "/deletedAccount/{id}")
    public ResponseEntity<ResponseObject> deleteAccount(@PathVariable long id) {
        Optional<Accounts> accountValue = accountsServiceImpl.getAccountById(id);
        String statusValue = accountValue.get().getStatus().getStatus();
        final int deactiveStatus = 2;
        Accounts account = accountsServiceImpl.updateAccountStatus(accountValue.get(), deactiveStatus);
        return statusValue != account.getStatus().getStatus() ? ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("200", MessEnum.SUCCESS.name(), "DELETED ACCOUNT SUCCESS", account))
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ResponseObject("500", MessEnum.UNSUCCESS.name(), "DELETED ACCOUNT FAIL", account));
    }

    @PatchMapping(value = "/updateRole/{id}/{role}")
    public ResponseEntity<ResponseObject> updateRole(@PathVariable long id, @PathVariable int role) {
        Optional<Accounts> accountValue = accountsServiceImpl.getAccountById(id);
        String roleValue = accountValue.get().getRole().getRole();
        Accounts account = accountsServiceImpl.updateAccountRole(accountValue.get(), role);
        return roleValue != account.getRole().getRole() ? ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("200", MessEnum.SUCCESS.name(), "CHANGE ROLE SUCCESS", account))
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ResponseObject("500", MessEnum.UNSUCCESS.name(), "CHANGE ROLE FAIL", account));
    }
}
