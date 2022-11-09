package com.nash.assignment.repositories.pagination;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.nash.assignment.modal.Order;

public interface OrderRepositoriesPagination extends PagingAndSortingRepository<Order,Long> {
    
}
