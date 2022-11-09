package com.nash.assignment.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nash.assignment.modal.OrderDetail;
import com.nash.assignment.modal.Product;
import com.nash.assignment.modal.RateProduct;

@Repository
public interface RateProductRepositories extends JpaRepository<RateProduct, Long> {

    List<RateProduct> findByProduct(Product product);

    RateProduct findByOrderDetail(OrderDetail orderDetail);

    RateProduct findByOrderDetailId(int id);
}
