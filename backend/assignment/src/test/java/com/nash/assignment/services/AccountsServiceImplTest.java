package com.nash.assignment.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nash.assignment.constant.StatusEnum;
import com.nash.assignment.dto.AccountDto;
import com.nash.assignment.exceptions.ObjectNotFoundException;
import com.nash.assignment.modal.Account;
import com.nash.assignment.modal.Role;
import com.nash.assignment.repositories.AccountRepositories;
import com.nash.assignment.repositories.RolesRepositories;

public class AccountsServiceImplTest {
    @Autowired
    AccountsServiceImpl accountsServiceImpl;
    @Autowired
    AccountRepositories accountRepositories;
    @Autowired
    RolesRepositories rolesRepositories;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    Role role;
    @Autowired
    AccountDto accountDto;

    private StatusEnum status;

    @BeforeEach
    void setUpBeforeTest() {
        accountRepositories = mock(AccountRepositories.class);
        rolesRepositories = mock(RolesRepositories.class);
        accountsServiceImpl = new AccountsServiceImpl(accountRepositories, rolesRepositories,
                passwordEncoder, modelMapper);
        role = mock(Role.class);
        modelMapper = mock(ModelMapper.class);
    }

    @Test
    void testGetAccountById_WhenAccountNull_ShouldThrowObjectNotFoundException() {
        Account account = new Account("01234533", "Test", "test", "123", role, status);
        when(accountRepositories.findById(1L)).thenReturn(null);
        ObjectNotFoundException expected = Assertions.assertThrows(ObjectNotFoundException.class,
                () -> accountsServiceImpl.getAccountById(account.getId()));
        Assertions.assertEquals("Cannot Find Account With ID: " + account.getId(), expected.getMessage());

    }

    @Test
    void testGetAccountById_WhenAccountNotNull_ShouldThrowAccountObject() {
        Account account = new Account("01234533", "Test", "test", "123", role, status);
        AccountDto accountDto = modelMapper.map(account, AccountDto.class);
        when(accountRepositories.findById(0L)).thenReturn(Optional.of(account));
        when(modelMapper.map(account,AccountDto.class)).thenReturn(accountDto);
        AccountDto result = accountsServiceImpl.getAccountById(account.getId());
        Assertions.assertEquals(accountDto, result);

    }

    @Test
    void testGetAllAccounts() {

    }

    @Test
    void testInsertAccounts() {

    }

    @Test
    void testUpdateAccountInformation() {

    }

    @Test
    void testUpdateAccountRole() {

    }

    @Test
    void testUpdateAccountStatus() {

    }
}
