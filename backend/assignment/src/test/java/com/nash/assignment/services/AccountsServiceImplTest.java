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
    AccountsServiceImpl accountsServiceImpl;
    AccountRepositories accountRepositories;
    RolesRepositories rolesRepositories;
    PasswordEncoder passwordEncoder;
    ModelMapper modelMapper;
    Role role;
    AccountDto accountDto;

    private StatusEnum status;

    @BeforeEach
    void setUpBeforeTest() {
        accountRepositories = mock(AccountRepositories.class);
        rolesRepositories = mock(RolesRepositories.class);
        role = mock(Role.class);
        modelMapper = mock(ModelMapper.class);
        accountsServiceImpl = new AccountsServiceImpl(accountRepositories, rolesRepositories,
                passwordEncoder, modelMapper);

    }

    @Test
    void testGetAccountById_WhenAccountNull_ShouldThrowObjectNotFoundException() {
        Account account = mock(Account.class);
        when(accountRepositories.findById(account.getId())).thenReturn(Optional.empty());

        ObjectNotFoundException expected = Assertions.assertThrows(ObjectNotFoundException.class,
                () -> accountsServiceImpl.getAccountById(account.getId()));

        Assertions.assertEquals("Cannot Find Account With ID: " + account.getId(), expected.getMessage());

    }

    @Test
    void testGetAccountById_WhenAccountNotNull_ShouldReturnAccountObject() {
        Account account = mock(Account.class);
        AccountDto accountDto = mock(AccountDto.class);

        when(accountRepositories.findById(0L)).thenReturn(Optional.of(account));
        when(modelMapper.map(account, AccountDto.class)).thenReturn(accountDto);

        AccountDto expected = accountsServiceImpl.getAccountById(account.getId());
        Assertions.assertEquals(accountDto, expected);

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
