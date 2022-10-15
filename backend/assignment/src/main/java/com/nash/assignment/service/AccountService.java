package com.nash.assignment.service;

import com.nash.assignment.modal.Accounts;

public interface AccountService {
    Accounts insertAccounts(Accounts account);

    Iterable<Accounts> getAllAccounts();

    Accounts updateAccountStatus(Accounts accountValue, int statusValue);

    Accounts updateAccountInformation(Accounts accountValue);

    Accounts updateAccountRole(Accounts accountValue, int roleValue);
}
