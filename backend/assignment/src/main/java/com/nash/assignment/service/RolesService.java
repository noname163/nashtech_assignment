package com.nash.assignment.service;

import com.nash.assignment.modal.Roles;

public interface RolesService {
    Roles insertRole(Roles role);

    Iterable<Roles> getAllRoles();
}
