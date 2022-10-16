package com.nash.assignment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nash.assignment.modal.Accounts;
import com.nash.assignment.modal.Status;

@Repository
public interface AccountRepositories extends JpaRepository<Accounts, Long> {
    Accounts findByPhoneNumber(String phoneNumber);

    Accounts findByUsername(String Username);

    Iterable<Accounts> findByStatus(Status status);
}
