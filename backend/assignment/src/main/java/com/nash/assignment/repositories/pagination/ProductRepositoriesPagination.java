package com.nash.assignment.repositories.pagination;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.nash.assignment.modal.Product;

@Repository
public interface ProductRepositoriesPagination extends PagingAndSortingRepository<Product, Long> {
    
}
