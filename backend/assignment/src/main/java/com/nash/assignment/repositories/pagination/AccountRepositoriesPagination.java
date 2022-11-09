package com.nash.assignment.repositories.pagination;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.nash.assignment.modal.Account;

public interface AccountRepositoriesPagination extends PagingAndSortingRepository<Account,Long>  {
    
}
