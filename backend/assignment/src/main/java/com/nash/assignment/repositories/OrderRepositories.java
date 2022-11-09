package com.nash.assignment.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nash.assignment.constant.OrderStatus;
import com.nash.assignment.modal.Account;
import com.nash.assignment.modal.Order;

public interface OrderRepositories extends JpaRepository<Order, Integer> {

    List<Order> findByAccount(Account accountValue);
    List<Order> findByAccountId(int id);
    Order findByStatus(OrderStatus statusValue);
}
