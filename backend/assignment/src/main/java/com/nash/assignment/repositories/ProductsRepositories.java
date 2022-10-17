package com.nash.assignment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nash.assignment.modal.Category;
import com.nash.assignment.modal.Product;

@Repository
public interface ProductsRepositories extends JpaRepository<Product, Long> {
    Product findByName(String name);

    Iterable<Product> findByCategories(Category categories);
}
