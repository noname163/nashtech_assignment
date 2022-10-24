package com.nash.assignment.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nash.assignment.modal.Order;
import com.nash.assignment.modal.OrderDetail;

public interface OrderDetailRepositories extends JpaRepository<OrderDetail, Integer> {
    List<OrderDetail> findByOrder(Order orderValue);
}
