package com.nash.assignment.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nash.assignment.modal.Account;
import com.nash.assignment.modal.Order;
import com.nash.assignment.modal.OrderDetail;
import com.nash.assignment.modal.Product;

public interface OrderDetailRepositories extends JpaRepository<OrderDetail, Integer> {

    List<OrderDetail> findByOrder(Order orderValue);

    List<OrderDetail> findByOrderId(int id);

    List<OrderDetail> findByOrderAndProduct(Order order, Product product);

    OrderDetail findByAccountAndProduct(Account account, Product product);

    OrderDetail findByAccountAndProductAndOrder(Account account, Product product, Order order);

    List<Product> findProductByOrder(Order order);

    List<OrderDetail> findByAccount(Account account);

}
