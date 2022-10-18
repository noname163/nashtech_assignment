package com.nash.assignment.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.nash.assignment.constant.RoleEnum;
import com.nash.assignment.constant.StatusEnum;
import com.nash.assignment.dto.AccountDto;
import com.nash.assignment.modal.Account;
import com.nash.assignment.modal.Role;
import com.nash.assignment.modal.Status;
import com.nash.assignment.repositories.AccountRepositories;
import com.nash.assignment.repositories.RolesRepositories;
import com.nash.assignment.repositories.StatusRepositories;
import com.nash.assignment.services.AccountsServiceImpl;

public class AccountsServiceImplTest {

    @Autowired
    AccountsServiceImpl accountsServiceImpl;
    @Autowired
    AccountRepositories accountRepositories;
    @Autowired
    RolesRepositories rolesRepositories;
    @Autowired
    StatusRepositories statusRepositories;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired ModelMapper modelMapper;
    @Autowired
    Role role;
    
    private StatusEnum status;

    @BeforeEach
    void setUpBeforeTest() {
        accountRepositories = mock(AccountRepositories.class);
        statusRepositories = mock(StatusRepositories.class);
        rolesRepositories = mock(RolesRepositories.class);
        accountsServiceImpl = new AccountsServiceImpl(accountRepositories, rolesRepositories,
                passwordEncoder, modelMapper);
        role = mock(Role.class);
    }

    @Test
    void testGetAllAccounts_WhenGetErrorAtRepositories_ShouldThrowNewRuntimeexception() {
        when(accountRepositories.findAll()).thenThrow(RuntimeException.class);
        RuntimeException expected = Assertions.assertThrows(RuntimeException.class, ()->accountsServiceImpl.getAllAccounts());
        Assertions.assertEquals("Error When Get All Account.", expected.getMessage());
    }

    @Test
    void testInsertAccounts_WhenUsernameEmpty_ShouldThrowNewRuntimeexception() {
        Account account = new Account("0122345", "kjkjk", "", "null", role, status);
        AccountDto accountDto = modelMapper.map(account, AccountDto.class);
        RuntimeException expected = Assertions.assertThrows(RuntimeException.class,
                () -> accountsServiceImpl.insertAccounts(accountDto));
        Assertions.assertEquals("Missing Information.", expected.getMessage());
    }

    @Test
    void testInsertAccounts_WhenFullnameEmpty_ShouldThrowNewRuntimeexception() {
        Account account = new Account("0123456", "", "null", "null", role, status);
        AccountDto accountDto = modelMapper.map(account, AccountDto.class);
        RuntimeException expected = Assertions.assertThrows(RuntimeException.class,
                () -> accountsServiceImpl.insertAccounts(accountDto));
        Assertions.assertEquals("Missing Information.", expected.getMessage());
    }

    @Test
    void testInsertAccounts_WhenPasswordEmpty_ShouldThrowNewRuntimeexception() {
        Account account = new Account("0123456", "lklklk", "kkjkj", "", role, status);
        AccountDto accountDto = modelMapper.map(account, AccountDto.class);
        RuntimeException expected = Assertions.assertThrows(RuntimeException.class,
                () -> accountsServiceImpl.insertAccounts(accountDto));
        Assertions.assertEquals("Missing Information.", expected.getMessage());
    }

    @Test
    void testInsertAccounts_WhenPhonenumberEmpty_ShouldThrowNewRuntimeexception() {
        Account account = new Account("", "adsasds", "null", "null", role, status);
        AccountDto accountDto = modelMapper.map(account, AccountDto.class);
        RuntimeException expected = Assertions.assertThrows(RuntimeException.class,
                () -> accountsServiceImpl.insertAccounts(accountDto));
        Assertions.assertEquals("Missing Information.", expected.getMessage());
    }

    @Test
    void testInsertAccounts_WhenPhonenumberNotValid_ShouldThrowNewRuntimeexception() {
        Account account = new Account("12345", "adsasds", "null", "null", role, status);
        AccountDto accountDto = modelMapper.map(account, AccountDto.class);
        RuntimeException expected = Assertions.assertThrows(RuntimeException.class,
                () -> accountsServiceImpl.insertAccounts(accountDto));
        Assertions.assertEquals("Phone Number Not Valid Format.", expected.getMessage());
    }

    @Test
    void testInsertAccounts_WhenFullNameNull_ShouldThrowNewRuntimeexception() {
        Account account = new Account("0998779755", null, "null", "null", role, status);
        AccountDto accountDto = modelMapper.map(account, AccountDto.class);
        RuntimeException expected = Assertions.assertThrows(RuntimeException.class,
                () -> accountsServiceImpl.insertAccounts(accountDto));
        Assertions.assertEquals("Missing Information.", expected.getMessage());
    }

    @Test
    void testInsertAccounts_WhenUsernameNull_ShouldThrowNewRuntimeexception() {
        Account account = new Account(null, "adsasds", null, "null", role, status);
        AccountDto accountDto = modelMapper.map(account, AccountDto.class);
        RuntimeException expected = Assertions.assertThrows(RuntimeException.class,
                () -> accountsServiceImpl.insertAccounts(accountDto));
        Assertions.assertEquals("Missing Information.", expected.getMessage());
    }

    @Test
    void testInsertAccounts_WhenPasswordNull_ShouldThrowNewRuntimeexception() {
        Account account = new Account(null, "adsasds", "null", null, role, status);
        AccountDto accountDto = modelMapper.map(account, AccountDto.class);
        RuntimeException expected = Assertions.assertThrows(RuntimeException.class,
                () -> accountsServiceImpl.insertAccounts(accountDto));
        Assertions.assertEquals("Missing Information.", expected.getMessage());
    }

    @Test
    void testInsertAccounts_WhenPhonenumberNull_ShouldThrowNewRuntimeexception() {
        Account account = new Account(null, "adsasds", "null", "null", role, status);
        AccountDto accountDto = modelMapper.map(account, AccountDto.class);
        RuntimeException expected = Assertions.assertThrows(RuntimeException.class,
                () -> accountsServiceImpl.insertAccounts(accountDto));
        Assertions.assertEquals("Missing Information.", expected.getMessage());
    }

    @Test
    void testUpdateAccountInformation_WhenAccountNull_ShouldThrowNewRuntimeexception() {
        Account account = new Account(null, "adsasds", "null", "null", role, status);
        when(accountRepositories.findByPhoneNumber(account.getPhoneNumber())).thenReturn(null);
        AccountDto accountDto = modelMapper.map(account, AccountDto.class);
        RuntimeException expected = Assertions.assertThrows(RuntimeException.class,
                () -> accountsServiceImpl.insertAccounts(accountDto));
        Assertions.assertEquals("Some How This Account Is Null.", expected.getMessage());
    }

    @Test
    void testUpdateAccountInformation_WhenMissingSetFullname_ShouldReturnAccount() {
        Account initAccount = mock(Account.class);
        Account expecAccount = mock(Account.class);
        when(accountRepositories.findByPhoneNumber(initAccount.getPhoneNumber())).thenReturn(initAccount);
        when(accountRepositories.save(initAccount)).thenReturn(expecAccount);
        AccountDto initAccountDto = modelMapper.map(initAccount, AccountDto.class);
        AccountDto result = accountsServiceImpl.updateAccountInformation(initAccountDto);
        verify(initAccount).setFullName(initAccount.getFullName());
        verify(initAccount).setAvatar(initAccount.getAvatar());
        verify(initAccount).setUsername(initAccount.getUsername());
        assertThat(result, is(expecAccount));
    }

    @Test
    void testUpdateAccountRole_WhenRoleValueLargeThan2_ShouldThrowRuntimeexception() {
        Account account = new Account("0123456", "adsasds", "null", "null", role, status);
        int roleValue = 3;
        AccountDto accountDto = modelMapper.map(account, AccountDto.class);
        RuntimeException expected = Assertions.assertThrows(RuntimeException.class,
                () -> accountsServiceImpl.updateAccountRole(accountDto, roleValue));
        Assertions.assertEquals("Role Not Valid.", expected.getMessage());
    }

    @Test
    void testUpdateAccountRole_WhenRoleValueSmallerThan1_ShouldThrowRuntimeexception() {
        Account account = new Account("0123456", "adsasds", "null", "null", role, status);
        int roleValue = 0;
        AccountDto accountDto = modelMapper.map(account, AccountDto.class);
        RuntimeException expected = Assertions.assertThrows(RuntimeException.class,
                () -> accountsServiceImpl.updateAccountRole(accountDto, roleValue));
        Assertions.assertEquals("Role Not Valid.", expected.getMessage());
    }

    @Test
    void testUpdateAccountRole_WhenRoleAccountNull_ShouldThrowRuntimeexception() {
        Account account = new Account("0123456", "adsasds", "null", "null", role, status);
        int roleValue = 1;
        when(accountRepositories.findByPhoneNumber("0123456")).thenReturn(null);
        AccountDto accountDto = modelMapper.map(account, AccountDto.class);
        RuntimeException expected = Assertions.assertThrows(RuntimeException.class,
                () -> accountsServiceImpl.updateAccountRole(accountDto, roleValue));
        Assertions.assertEquals("Some How This Account Is Null.", expected.getMessage());
    }

    @Test
    void testUpdateAccountRole_WhenDataValid_ShouldReturnAccountWithRoleAdmin() {
        Role adminRole = new Role(RoleEnum.ROLE_USER);
        when(rolesRepositories.findByRole(RoleEnum.ROLE_ADMIN)).thenReturn(adminRole);
        Account initAccount = mock(Account.class);
        Account expecAccount = new Account("0123456", "null", "null", "null", adminRole, status);
        int roleValue = 1;
        when(accountRepositories.findByPhoneNumber(initAccount.getPhoneNumber())).thenReturn(initAccount);
        when(accountRepositories.save(initAccount)).thenReturn(expecAccount);
        AccountDto accountDto = modelMapper.map(initAccount, AccountDto.class);
        AccountDto result = accountsServiceImpl.updateAccountRole(accountDto, roleValue);
        verify(initAccount).setRole(adminRole);
        Assertions.assertEquals(result.getRole().getRole(), adminRole.getRole());
    }

    @Test
    void testUpdateAccountRole_WhenDataValid_ShouldReturnAccountWithRoleUser() {
        Role userRoles = new Role(RoleEnum.ROLE_USER);
        Role adminRole = new Role(RoleEnum.ROLE_USER);
        when(rolesRepositories.findByRole(RoleEnum.ROLE_USER)).thenReturn(userRoles);
        Account initAccount = mock(Account.class);
        Account expecAccount = new Account("0123456", "null", "null", "null", adminRole, status);
        int roleValue = 2;
        when(accountRepositories.findByPhoneNumber(initAccount.getPhoneNumber())).thenReturn(initAccount);
        when(accountRepositories.save(initAccount)).thenReturn(expecAccount);
        AccountDto accountDto = modelMapper.map(initAccount, AccountDto.class);
        AccountDto result = accountsServiceImpl.updateAccountRole(accountDto, roleValue);
        verify(initAccount).setRole(userRoles);
        Assertions.assertEquals(result.getRole().getRole(), userRoles.getRole());
    }

    @Test
    void testUpdateAccountRole_WhenStatusValueLargeThan2_ShouldThrowRuntimeexception() {
        Account account = new Account("0123456", "adsasds", "null", "null", role, status);
        int statusValue = 3;
        AccountDto accountDto = modelMapper.map(account, AccountDto.class);
        RuntimeException expected = Assertions.assertThrows(RuntimeException.class,
                () -> accountsServiceImpl.updateAccountStatus(accountDto, statusValue));
        Assertions.assertEquals("Status Not Valid.", expected.getMessage());
    }

    @Test
    void testUpdateAccountStatus_WhenStatusValueSmallerThan1_ShouldThrowRuntimeexception() {
        Account account = new Account("0123456", "adsasds", "null", "null", role, status);
        int statusValue = 0;
        AccountDto accountDto = modelMapper.map(account, AccountDto.class);
        RuntimeException expected = Assertions.assertThrows(RuntimeException.class,
                () -> accountsServiceImpl.updateAccountStatus(accountDto, statusValue));
        Assertions.assertEquals("Status Not Valid.", expected.getMessage());
    }

    @Test
    void testUpdateAccountStatus_WhenAccountNull_ShouldThrowRuntimeexception() {
        Account account = new Account("0123456", "adsasds", "null", "null", role, status);
        int statusValue = 1;
        when(accountRepositories.findByPhoneNumber("0123456")).thenReturn(null);
        AccountDto accountDto = modelMapper.map(account, AccountDto.class);
        RuntimeException expected = Assertions.assertThrows(RuntimeException.class,
                () -> accountsServiceImpl.updateAccountStatus(accountDto, statusValue));
        Assertions.assertEquals("Error When Update Account Status", expected.getMessage());
    }

    @Test
    void testUpdateAccountStatus_WhenDataValid_ShouldReturnAccountWithStatusActive() {
        StatusEnum activeStatus = StatusEnum.ACTIVE ;

        Account initAccount = mock(Account.class);
        Account expecAccount = new Account("0123456", "null", "null", "null", role, activeStatus);
        int statusValue = 1;
        when(accountRepositories.findByPhoneNumber(initAccount.getPhoneNumber())).thenReturn(initAccount);
        when(accountRepositories.save(initAccount)).thenReturn(expecAccount);
        AccountDto accountDto = modelMapper.map(initAccount, AccountDto.class);
        AccountDto result = accountsServiceImpl.updateAccountStatus(accountDto, statusValue);
        verify(initAccount).setStatus(activeStatus);
        ;
        Assertions.assertEquals(result.getStatus(), activeStatus);
    }

    @Test
    void testUpdateAccountStatus_WhenDataValid_ShouldReturnAccountWithStatusDeactive() {
        StatusEnum deactive = StatusEnum.DEACTIVE;

        Account initAccount = mock(Account.class);
        Account expecAccount = new Account("0123456", "null", "null", "null", role, deactive);
        int statusValue = 2;
        when(accountRepositories.findByPhoneNumber(initAccount.getPhoneNumber())).thenReturn(initAccount);
        when(accountRepositories.save(initAccount)).thenReturn(expecAccount);
        AccountDto accountDto = modelMapper.map(initAccount, AccountDto.class);
        AccountDto result = accountsServiceImpl.updateAccountStatus(accountDto, statusValue);
        verify(initAccount).setStatus(deactive);
        ;
        Assertions.assertEquals(result.getStatus(), deactive);
    }

}
