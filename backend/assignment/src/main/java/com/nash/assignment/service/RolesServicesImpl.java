package com.nash.assignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nash.assignment.model.Roles;
import com.nash.assignment.repositories.RolesRepositories;

@Service
public class RolesServicesImpl implements RolesService {

    @Autowired
    RolesRepositories rolesRepositories;

    @Override
    public Roles insertRole(Roles role) {
        Roles insert = rolesRepositories.save(role);
        return insert;
    }

    @Override
    public Iterable<Roles> getAllRoles() {
        Iterable<Roles> list = rolesRepositories.findAll();
        return list;
    }

}
