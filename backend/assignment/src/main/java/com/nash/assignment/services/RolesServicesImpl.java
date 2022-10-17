package com.nash.assignment.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nash.assignment.modal.Role;
import com.nash.assignment.repositories.RolesRepositories;

@Service
public class RolesServicesImpl implements RolesService {

    @Autowired
    RolesRepositories rolesRepositories;

    @Override
    public Role insertRole(Role role) {
        Role insert = rolesRepositories.save(role);
        return insert;
    }

    @Override
    public Iterable<Role> getAllRoles() {
        Iterable<Role> list = rolesRepositories.findAll();
        return list;
    }

}
