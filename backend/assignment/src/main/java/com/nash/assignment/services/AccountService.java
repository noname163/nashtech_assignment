package com.nash.assignment.services;

import java.util.List;

import com.nash.assignment.dto.AccountDto;
import com.nash.assignment.modal.Account;

public interface AccountService {
    AccountDto insertAccounts(AccountDto account);

    List<Account> getAllAccounts();

    Account updateAccountStatus(Account accountValue, int statusValue);

    Account updateAccountInformation(Account accountValue);

    Account updateAccountRole(Account accountValue, int roleValue);
}
