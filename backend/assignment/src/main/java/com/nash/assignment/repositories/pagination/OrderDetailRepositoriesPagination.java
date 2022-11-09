package com.nash.assignment.repositories.pagination;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.nash.assignment.modal.OrderDetail;

public interface OrderDetailRepositoriesPagination extends PagingAndSortingRepository<OrderDetail, Long> {
    
}
