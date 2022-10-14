package com.nash.assignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nash.assignment.model.Accounts;
import com.nash.assignment.repositories.AccountRepositories;

@Service
public class AccountsServiceImpl implements AccountService {
    @Autowired
    AccountRepositories accountRepositories;

    @Override
    public Accounts insertAccounts(Accounts accounts) {
        Accounts insert = accountRepositories.save(accounts);
        return insert;
    }

    @Override
    public Iterable<Accounts> getAllAccounts() {
        Iterable<Accounts> list = accountRepositories.findAll();
        return list;
    }

}
