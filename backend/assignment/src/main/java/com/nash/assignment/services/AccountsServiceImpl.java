package com.nash.assignment.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.nash.assignment.constant.RoleEnum;
import com.nash.assignment.constant.StatusEnum;
import com.nash.assignment.database.Database;
import com.nash.assignment.dto.AccountDto;
import com.nash.assignment.modal.Account;
import com.nash.assignment.modal.Role;
import com.nash.assignment.modal.Status;
import com.nash.assignment.repositories.AccountRepositories;
import com.nash.assignment.repositories.RolesRepositories;
import com.nash.assignment.repositories.StatusRepositories;

@Service
public class AccountsServiceImpl implements AccountService, UserDetailsService {
    @Autowired
    AccountRepositories accountRepositories;
    @Autowired
    RolesRepositories rolesRepositories;
    @Autowired
    ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(Database.class);

    public AccountsServiceImpl(AccountRepositories accountRepositories, RolesRepositories rolesRepositories, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.accountRepositories = accountRepositories;
        this.rolesRepositories = rolesRepositories;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public AccountDto insertAccounts(@Validated AccountDto accountDto) {
        if (accountRepositories.findByPhoneNumber(accountDto.getPhoneNumber()) != null) {
            throw new RuntimeException("This Phonenumber Already Exist.");
        }
        String regex = "^(0|84)(2(0[3-9]|1[0-6|8|9]|2[0-2|5-9]|3[2-9]|4[0-9]|5[1|2|4-9]|6[0-3|9]|7[0-7]|8[0-9]|9[0-4|6|7|9])|3[2-9]|5[5|6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])([0-9]{7})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(accountDto.getPhoneNumber());
        if (!matcher.matches()) {
            throw new RuntimeException("Phone Number Not Valid Format.");
        }
        if (accountDto.getRole() == null) {
            Role role = new Role(RoleEnum.ROLE_USER.name());
            accountDto.setRole(role);
        }
            accountDto.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        Account account  = modelMapper.map(accountDto,Account.class);
        Account insert = accountRepositories.save(account);
        
        return modelMapper.map(insert, AccountDto.class);
    }

    @Override
    public List<Account> getAllAccounts() {
        List<Account> list = null;
        list = accountRepositories.findAll();

        return list;
    }

    public Account getAccountById(long id) {
        Optional<Account> accountOtp =  accountRepositories.findById(id);
        if(accountOtp.empty() == null){
            throw new RuntimeException("Cannot Find Account With ID: " + id);
        }
        Account account = accountOtp.get();
        return account;
    }

    @Override
    public Account updateAccountStatus(Account accountValue, int statusValue) {
        Account account = null;
        if (statusValue < 1 || statusValue > 2) {
            throw new RuntimeException("Status Not Valid.");
        }
        account = accountRepositories.findByPhoneNumber(accountValue.getPhoneNumber());
        StatusEnum status = null;
        if (statusValue == 1) {
            status = StatusEnum.Active;
        } else if (statusValue == 2) {
            status = StatusEnum.Deactivate;
        }
        account.setStatus(status);
        account = accountRepositories.save(account);
        return account;
    }

    @Override
    public Account updateAccountInformation(Account accountValue) {
        Account account = accountRepositories.findByPhoneNumber(accountValue.getPhoneNumber());
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
    public Account updateAccountRole(Account accountValue, int roleValue) {
        Account account = accountRepositories.findByPhoneNumber(accountValue.getPhoneNumber());
        if (roleValue <= 0 || roleValue > 2) {
            throw new RuntimeException("Role Not Valid.");
        }
        if (account == null) {
            throw new RuntimeException("Some How This Account Is Null.");

        }
        try {
            Role role = null;
            if (roleValue == 1) {
                role = rolesRepositories.findByRole(RoleEnum.ROLE_ADMIN.name());
            }
            if (roleValue == 2) {
                role = rolesRepositories.findByRole(RoleEnum.ROLE_USER.name());
            }
            account.setRole(role);
            account = accountRepositories.save(account);
        } catch (Exception e) {
            throw new RuntimeException("Error When Update Account Role", e);
        }
        return account;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepositories.findByUsername(username);
        if (account == null) {
            logger.error("Username Not Found");
            throw new UsernameNotFoundException("Username Not found");
        } else {
            logger.info("User found in the database: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(account.getRole().getRole()));
        return new org.springframework.security.core.userdetails.User(account.getUsername(), account.getPassword(),
                authorities);
    }

}
