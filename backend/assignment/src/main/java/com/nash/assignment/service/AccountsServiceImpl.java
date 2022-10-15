package com.nash.assignment.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nash.assignment.constant_variable.RoleEnum;
import com.nash.assignment.constant_variable.StatusEnum;
import com.nash.assignment.modal.Accounts;
import com.nash.assignment.modal.Roles;
import com.nash.assignment.modal.Status;
import com.nash.assignment.repositories.AccountRepositories;

import net.bytebuddy.implementation.bytecode.Throw;

@Service
public class AccountsServiceImpl implements AccountService {
    @Autowired
    AccountRepositories accountRepositories;

    public AccountsServiceImpl(AccountRepositories accountRepositories) {
        this.accountRepositories = accountRepositories;
    }

    @Override
    public Accounts insertAccounts(Accounts accounts) {
        if (accounts.getFullName() == null
                || accounts.getPhoneNumber() == null || accounts.getUsername() == null
                || accounts.getPassword() == null || accounts.getFullName().equals("")
                || accounts.getPhoneNumber().equals("")
                || accounts.getPassword().equals("")
                || accounts.getUsername().equals("")) {
            throw new RuntimeException("Missing Information.");
        }
        if (accountRepositories.findByPhoneNumber(accounts.getPhoneNumber()) != null) {
            throw new RuntimeException("This Phonenumber Already Exist.");
        }
        String regex = "^(0|84)(2(0[3-9]|1[0-6|8|9]|2[0-2|5-9]|3[2-9]|4[0-9]|5[1|2|4-9]|6[0-3|9]|7[0-7]|8[0-9]|9[0-4|6|7|9])|3[2-9]|5[5|6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])([0-9]{7})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(accounts.getPhoneNumber());
        if (!matcher.matches()) {
            throw new RuntimeException("Phone Number Not Valid Format.");
        }
        if (accounts.getRole() == null) {
            Roles role = new Roles(RoleEnum.ROLE_USER.name());
            accounts.setRole(role);
        }
        Accounts insert = null;
        try {
            insert = accountRepositories.save(accounts);
        } catch (Exception e) {
            throw new RuntimeException("Error When Insert Account.", e);
        }
        return insert;
    }

    @Override
    public Iterable<Accounts> getAllAccounts() {
        Iterable<Accounts> list = null;
        try {
            list = accountRepositories.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error When Get All Account.", e);
        }
        return list;
    }

    @Override
    public Accounts updateAccountStatus(Accounts accountValue, int statusValue) {
        Accounts account = null;
        if (statusValue < 1 || statusValue > 2) {
            throw new RuntimeException("Status Not Valid.");
        }
        try {
            account = accountRepositories.findByPhoneNumber(accountValue.getPhoneNumber());
            Status status = null;
            if (statusValue == 1) {
                status = new Status(StatusEnum.Active.name());
            } else if (statusValue == 2) {
                status = new Status(StatusEnum.Deactivate.name());
            }
            account.setStatus(status);
            accountRepositories.save(account);
        } catch (Exception e) {
            throw new RuntimeException("Error When Update Account Status", e);
        }
        return account;
    }

    @Override
    public Accounts updateAccountInformation(Accounts accountValue) {
        Accounts account = accountRepositories.findByPhoneNumber(accountValue.getPhoneNumber());
        if (account == null) {
            throw new RuntimeException("Some How This Account Is Null.");
        }
        try {
            account.setFullName(accountValue.getFullName());
            account.setUsername(accountValue.getUsername());
            account.setAvatar(accountValue.getAvatar());
            account = accountRepositories.save(account);
        } catch (Exception e) {
            throw new RuntimeException("Error When Update Account Information.", e);
        }
        return account;
    }

    @Override
    public Accounts updateAccountRole(Accounts accountValue, int roleValue) {
        Accounts account = accountRepositories.findByPhoneNumber(accountValue.getPhoneNumber());
        if (roleValue <= 0 || roleValue > 2) {
            throw new RuntimeException("Role Not Valid.");
        }
        if (account == null) {
            throw new RuntimeException("Some How This Account Is Null.");

        }
        try {
            Roles role = null;
            if (roleValue == 1) {
                role = new Roles(RoleEnum.ROLE_ADMIN.name());
            }
            if (roleValue == 2) {
                role = new Roles(RoleEnum.ROLE_USER.name());
            }
            account.setRole(role);
            account = accountRepositories.save(account);
        } catch (Exception e) {
            throw new RuntimeException("Error When Update Account Role", e);
        }
        return account;
    }

}
