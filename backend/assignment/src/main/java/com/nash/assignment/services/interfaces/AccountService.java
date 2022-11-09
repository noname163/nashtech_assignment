package com.nash.assignment.services.interfaces;

import java.util.List;

import com.nash.assignment.constant.AccountStatus;
import com.nash.assignment.dto.AccountDto;
import com.nash.assignment.modal.Account;

public interface AccountService {

    AccountDto insertAccounts(AccountDto account);

    List<AccountDto> getAllAccounts();

    AccountDto updateAccountStatus(long id, AccountStatus statusValue);

    AccountDto updateAccountInformation(AccountDto accountValue);

    AccountDto updateAccountRole(long id);
}
