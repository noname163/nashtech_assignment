package com.nash.assignment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nash.assignment.constant.StatusEnum;
import com.nash.assignment.modal.Account;
import com.nash.assignment.modal.Order;

public interface OrderRepositories extends JpaRepository<Order, Integer> {

    Order findByAccount(Account accountValue);

    Order findByStatus(StatusEnum statusValue);
}
