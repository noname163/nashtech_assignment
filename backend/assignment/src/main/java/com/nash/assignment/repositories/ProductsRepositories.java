package com.nash.assignment.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nash.assignment.modal.Category;
import com.nash.assignment.modal.Product;

@Repository
public interface ProductsRepositories extends JpaRepository<Product, Long> {

    Product findByName(String name);

    List<Product> findByCategories(Category categories);
}
