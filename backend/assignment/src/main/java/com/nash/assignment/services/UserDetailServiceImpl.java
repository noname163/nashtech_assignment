package com.nash.assignment.services;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nash.assignment.constant.AccountStatus;
import com.nash.assignment.database.Database;
import com.nash.assignment.modal.Account;
import com.nash.assignment.repositories.AccountRepositories;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    public UserDetailServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    private static final Logger logger = LoggerFactory.getLogger(Database.class);
    @Autowired
    AccountRepositories accountRepositories;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepositories.findByEmail(email);
        if (account == null) {
            logger.error("Username Not Found");
            throw new UsernameNotFoundException("Username Not found");
        } else {
            logger.info("User found in the database: {}", email);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(account.getRole().getRole().name()));
        return new User(account.getEmail(), account.getPassword(),
                authorities);
    }

}
