package com.nash.assignment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nash.assignment.constant.AccountStatus;
import com.nash.assignment.modal.Account;

@Repository
public interface AccountRepositories extends JpaRepository<Account, Long> {

    Account findByPhoneNumber(String phoneNumber);

    Account findByUsername(String Username);

    Iterable<Account> findByStatus(AccountStatus status);

    Account findByEmail(String email);
}
