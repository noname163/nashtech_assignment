package com.nash.assignment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nash.assignment.constant.RoleEnum;
import com.nash.assignment.modal.Role;

@Repository
public interface RolesRepositories extends JpaRepository<Role, Long> {

    Role findByRole(RoleEnum role);
}
