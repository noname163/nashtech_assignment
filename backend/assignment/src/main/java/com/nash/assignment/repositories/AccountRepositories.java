package com.nash.assignment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nash.assignment.constant.StatusEnum;
import com.nash.assignment.modal.Account;

@Repository
public interface AccountRepositories extends JpaRepository<Account, Long> {

    Account findByPhoneNumber(String phoneNumber);

    Account findByUsername(String Username);

    Iterable<Account> findByStatus(StatusEnum status);

    Account findByEmail(String email);
}
