package com.nash.assignment.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nash.assignment.constant.UserRole;
import com.nash.assignment.exceptions.InformationNotValidException;
import com.nash.assignment.modal.Role;
import com.nash.assignment.repositories.RolesRepositories;
import com.nash.assignment.services.interfaces.RolesService;
import java.util.Collections;

@Service
public class RolesServicesImpl implements RolesService {

    @Autowired
    RolesRepositories rolesRepositories;
    

    public RolesServicesImpl(RolesRepositories rolesRepositories) {
        this.rolesRepositories = rolesRepositories;
    }

    @Override
    public Role insertRole(Role role) {
        if(rolesRepositories.findByRole(role.getRole())!=null){
            throw new InformationNotValidException("Already Exist Role Name: " + role.getRole());
        }
        return rolesRepositories.save(role);
    }

    @Override
    public List<Role> getAllRoles() {
        List<Role> result = rolesRepositories.findAll();
        if(result.isEmpty()){
            return Collections.emptyList();
        }
        return result;
    }

}
