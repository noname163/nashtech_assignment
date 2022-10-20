package com.nash.assignment.services.interfaces;

import java.util.List;

import com.nash.assignment.dto.AccountDto;
import com.nash.assignment.modal.Account;

public interface AccountService {
    AccountDto insertAccounts(AccountDto account);

    List<Account> getAllAccounts();

    AccountDto updateAccountStatus(long id, int statusValue);

    AccountDto updateAccountInformation(AccountDto accountValue);

    AccountDto updateAccountRole(long id, int roleValue);
}
