package com.nash.assignment.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.nash.assignment.constant.UserRole;
import com.nash.assignment.exceptions.InformationNotValidException;
import com.nash.assignment.modal.Role;
import com.nash.assignment.repositories.RolesRepositories;

public class RolesServicesImplTest {

    RolesRepositories rolesRepositories;

    RolesServicesImpl rolesServicesImpl;

    Role role;

    @BeforeEach
    void setUp(){
        rolesRepositories = mock(RolesRepositories.class);
        rolesServicesImpl = new RolesServicesImpl(rolesRepositories);
    }

    @Test
    void getAllRoles_ShouldReturnListRole() {
        List<Role> expected = mock(List.class);
        when(rolesRepositories.findAll()).thenReturn(expected);
        List<Role> actual = rolesServicesImpl.getAllRoles();
        assertThat(expected, is(actual));
    }
    @Test
    void getAllRoles_WhenEmpty_ShouldReturnEmptyCollection() {
        List<Role> expected = Collections.emptyList();
        when(rolesRepositories.findAll()).thenReturn(expected);
        List<Role> actual = rolesServicesImpl.getAllRoles();
        assertThat(expected, is(actual));
    }

    @Test
    void insertRole_ShouldReturnRoleObject() {
        role = new Role(UserRole.ROLE_ADMIN);
        when(rolesRepositories.findByRole(role.getRole())).thenReturn(null);
        when(rolesRepositories.save(role)).thenReturn(role);
        Role actual = rolesServicesImpl.insertRole(role);
        assertThat(role, is(actual));

    }
    @Test
    void insertRole_WhenRoleExist_ShouldThrowInvalidInformationException() {
        role = new Role(UserRole.ROLE_ADMIN);
        when(rolesRepositories.findByRole(role.getRole())).thenReturn(role);
        InformationNotValidException actual = assertThrows(InformationNotValidException.class, ()->rolesServicesImpl.insertRole(role));
        assertThat("Already Exist Role Name: " + role.getRole(), is(actual.getMessage()));

    }
}
