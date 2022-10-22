package com.nash.assignment.services;

import java.io.ObjectInputFilter.Status;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

import com.nash.assignment.constant.RoleEnum;
import com.nash.assignment.constant.StatusEnum;
import com.nash.assignment.database.Database;
import com.nash.assignment.dto.AccountDto;
import com.nash.assignment.exceptions.InformationNotValidException;
import com.nash.assignment.exceptions.ObjectNotFoundException;
import com.nash.assignment.modal.Account;
import com.nash.assignment.modal.Role;
import com.nash.assignment.repositories.AccountRepositories;
import com.nash.assignment.repositories.RolesRepositories;
import com.nash.assignment.services.interfaces.AccountService;

@Service
public class AccountsServiceImpl implements AccountService, UserDetailsService {

    AccountRepositories accountRepositories;

    RolesRepositories rolesRepositories;

    ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(Database.class);

    @Autowired
    public AccountsServiceImpl(AccountRepositories accountRepositories, RolesRepositories rolesRepositories,
            PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.accountRepositories = accountRepositories;
        this.rolesRepositories = rolesRepositories;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
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
        Role role = rolesRepositories.findByRole(RoleEnum.ROLE_USER);
        accountDto.setRole(role);
        accountDto.setStatus(StatusEnum.ACTIVE);
        accountDto.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        Account account = modelMapper.map(accountDto, Account.class);
        account = accountRepositories.save(account);

        return modelMapper.map(account, AccountDto.class);
    }

    @Override
    public List<Account> getAllAccounts() {
        List<Account> list = accountRepositories.findAll();
        if (list == null || list.isEmpty()) {
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
    public AccountDto updateAccountStatus(long id, int statusValue) {
        Optional<Account> accountOtp = accountRepositories.findById(id);
        if (statusValue < 1 || statusValue > 2) {
            throw new InformationNotValidException("Status Not Valid.");
        }
        if (accountOtp.isEmpty()) {
            throw new ObjectNotFoundException("Cannot Find Account With Id: " + id);
        }
        Account account = accountOtp.get();
        StatusEnum status = null;
        if (statusValue == 1) {
            status = StatusEnum.ACTIVE;
        }
        if (statusValue == 2) {
            status = StatusEnum.DEACTIVE;
        }
        account.setStatus(status);

        account = accountRepositories.save(account);
        return modelMapper.map(account, AccountDto.class);
    }

    @Override
    public AccountDto updateAccountInformation(AccountDto accountValue) {
        Account account = accountRepositories.findByPhoneNumber(accountValue.getPhoneNumber());
        if (account == null) {
            throw new ObjectNotFoundException("Cannot Find Account With Phonenumber: " + accountValue.getPhoneNumber());
        }
        account.setFullName(accountValue.getFullName());
        account.setUsername(accountValue.getUsername());
        account.setAvatar(accountValue.getAvatar());
        account = accountRepositories.save(account);

        return modelMapper.map(account, AccountDto.class);
    }

    @Override
    public AccountDto updateAccountRole(long id, int roleValue) {
        Optional<Account> accountOtp = accountRepositories.findById(id);
        if (roleValue <= 0 || roleValue > 2) {
            throw new InformationNotValidException("Role Not Valid");
        }
        if (accountOtp.isEmpty()) {
            throw new ObjectNotFoundException("Cannot Found Account With Id: " + id);

        }
        Account account = accountOtp.get();
        Role role = null;
        if (roleValue == 1) {
            role = rolesRepositories.findByRole(RoleEnum.ROLE_ADMIN);
        }
        if (roleValue == 2) {
            role = rolesRepositories.findByRole(RoleEnum.ROLE_USER);
        }
        account.setRole(role);
        account = accountRepositories.save(account);

        return modelMapper.map(account, AccountDto.class);
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
        authorities.add(new SimpleGrantedAuthority(account.getRole().getRole().name()));
        return new org.springframework.security.core.userdetails.User(account.getUsername(), account.getPassword(),
                authorities);
    }

}
