package com.nash.assignment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nash.assignment.model.Roles;


@Repository
public interface RolesRepositories extends JpaRepository<Roles,Long> {
    Roles findByRole(String role);
}
