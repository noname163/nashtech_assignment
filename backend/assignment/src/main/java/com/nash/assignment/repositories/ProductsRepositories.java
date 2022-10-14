package com.nash.assignment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nash.assignment.model.Categories;
import com.nash.assignment.model.Products;

@Repository
public interface ProductsRepositories extends JpaRepository<Products, Long> {
    Products findByName(String name);

    Iterable<Products> findByCategories(Categories categories);
}
