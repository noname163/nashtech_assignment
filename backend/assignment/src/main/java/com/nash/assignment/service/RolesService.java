package com.nash.assignment.service;

import com.nash.assignment.model.Roles;

public interface RolesService {
    Roles insertRole(Roles role);

    Iterable<Roles> getAllRoles();
}
