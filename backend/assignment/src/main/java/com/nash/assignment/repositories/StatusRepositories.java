package com.nash.assignment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nash.assignment.modal.Status;

@Repository
public interface StatusRepositories extends JpaRepository<Status,Long>{
    Status findByStatus(String status);
}
