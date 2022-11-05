package com.nash.assignment.services;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nash.assignment.constant.UserRole;
import com.nash.assignment.constant.AccountStatus;
import com.nash.assignment.dto.AccountDto;
import com.nash.assignment.exceptions.InformationNotValidException;
import com.nash.assignment.exceptions.ObjectNotFoundException;
import com.nash.assignment.modal.Account;
import com.nash.assignment.modal.Role;
import com.nash.assignment.repositories.AccountRepositories;
import com.nash.assignment.repositories.RolesRepositories;
import com.nash.assignment.services.interfaces.AccountService;

@Service
public class AccountsServiceImpl implements AccountService{

    AccountRepositories accountRepositories;

    RolesRepositories rolesRepositories;

    ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountsServiceImpl(AccountRepositories accountRepositories, RolesRepositories rolesRepositories,
            PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.accountRepositories = accountRepositories;
        this.rolesRepositories = rolesRepositories;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }
    

    public AccountsServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public Account insert(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepositories.save(account);
    }

    @Override
    public AccountDto insertAccounts(AccountDto accountDto) {
        if (accountRepositories.findByPhoneNumber(accountDto.getPhoneNumber()) != null) {
            throw new InformationNotValidException("This Phonenumber Already Exist.");
        }
        Role role = rolesRepositories.findByRole(UserRole.ROLE_USER);
        accountDto.setRole(role);
        accountDto.setStatus(AccountStatus.ACTIVE);
        accountDto.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        Account account = modelMapper.map(accountDto, Account.class);
        account = accountRepositories.save(account);

        return modelMapper.map(account, AccountDto.class);
    }

    @Override
    public List<Account> getAllAccounts() {
        List<Account> list = accountRepositories.findAll();
        if (null == list || list.isEmpty()) {
            throw new ObjectNotFoundException("Account List Is Empty.");
        }
        return list;
    }

    public AccountDto getAccountById(long id) {
        Optional<Account> accountOtp = accountRepositories.findById(id);
        if (accountOtp.isEmpty()) {
            throw new ObjectNotFoundException("Cannot Find Account With ID: " + id);
        }
        Account account = accountOtp.get();
        return modelMapper.map(account, AccountDto.class);
    }

    public AccountDto getAccountByPhonenumber(String phone) {
        Optional<Account> accountOtp = Optional.of(accountRepositories.findByPhoneNumber(phone));
        if (accountOtp.isEmpty()) {
            throw new ObjectNotFoundException("Cannot Find Account With Phone: " + phone);
        }
        Account account = accountOtp.get();
        return modelMapper.map(account, AccountDto.class);
    }

    @Override
    public AccountDto updateAccountStatus(long id, AccountStatus statusValue) {
        Optional<Account> accountOtp = accountRepositories.findById(id);
        if (accountOtp.isEmpty()) {
            throw new ObjectNotFoundException("Cannot Find Account With Id: " + id);
        }
        Account account = accountOtp.get();
        account.setStatus(statusValue);

        accountRepositories.save(account);
        return modelMapper.map(account, AccountDto.class);
    }

    @Override
    public AccountDto updateAccountInformation(AccountDto accountValue) {
        Account account = accountRepositories.findByPhoneNumber(accountValue.getPhoneNumber());
        if (account == null) {
            throw new ObjectNotFoundException("Cannot Find Account With Phonenumber: " + accountValue.getPhoneNumber());
        }
        account.setFullName(accountValue.getFullName());
        account.setImage(accountValue.getImage());
        account = accountRepositories.save(account);

        return modelMapper.map(account, AccountDto.class);
    }

    @Override
    public AccountDto updateAccountRole(long id) {
        Optional<Account> accountOtp = accountRepositories.findById(id);

        if (accountOtp.isEmpty()) {
            throw new ObjectNotFoundException("Cannot Found Account With Id: " + id);

        }
        Account account = accountOtp.get();
        Role role = rolesRepositories.findByRole(UserRole.ROLE_ADMIN);
        if (role == null) {
            throw new ObjectNotFoundException("Cannot Found Role: " + UserRole.ROLE_ADMIN.toString());
        }
        account.setRole(role);
        accountRepositories.save(account);

        return modelMapper.map(account, AccountDto.class);
    }

}
