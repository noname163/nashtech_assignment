package com.nash.assignment.service;

import com.nash.assignment.model.Accounts;

public interface AccountService {
    Accounts insertAccounts(Accounts accounts);
    Iterable<Accounts> getAllAccounts();
}
