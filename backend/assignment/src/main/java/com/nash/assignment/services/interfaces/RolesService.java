package com.nash.assignment.services.interfaces;

import com.nash.assignment.modal.Role;

public interface RolesService {

    Role insertRole(Role role);

    Iterable<Role> getAllRoles();
}
