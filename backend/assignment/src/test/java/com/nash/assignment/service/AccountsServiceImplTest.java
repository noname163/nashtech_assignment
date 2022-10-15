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
import org.springframework.beans.factory.annotation.Autowired;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.nash.assignment.constant_variable.RoleEnum;
import com.nash.assignment.modal.Accounts;
import com.nash.assignment.modal.Roles;
import com.nash.assignment.modal.Status;
import com.nash.assignment.repositories.AccountRepositories;

public class AccountsServiceImplTest {

    @Autowired
    AccountsServiceImpl accountsServiceImpl;
    @Autowired
    AccountRepositories accountRepositories;
    @Autowired
    Roles role;
    @Autowired
    Status status;

    @Captor
    ArgumentCaptor<Roles> roleCaptor;

    @BeforeEach
    void setUpBeforeTest() {
        accountRepositories = mock(AccountRepositories.class);
        accountsServiceImpl = new AccountsServiceImpl(accountRepositories);
        role = mock(Roles.class);
        status = mock(Status.class);
    }

    @Test
    void testGetAllAccounts_WhenGetErrorAtRepositories_ShouldThrowNewRuntimeexception() {
        when(accountRepositories.findAll()).thenThrow(RuntimeException.class);
        RuntimeException expected = Assertions.assertThrows(RuntimeException.class, ()->accountsServiceImpl.getAllAccounts());
        Assertions.assertEquals("Error When Get All Account.", expected.getMessage());
    }

    @Test
    void testInsertAccounts_WhenUsernameEmpty_ShouldThrowNewRuntimeexception() {
        Accounts account = new Accounts("0122345", "kjkjk", "", "null", role, status);
        RuntimeException expected = Assertions.assertThrows(RuntimeException.class,
                () -> accountsServiceImpl.insertAccounts(account));
        Assertions.assertEquals("Missing Information.", expected.getMessage());
    }
    @Test
    void testInsertAccounts_WhenFullnameEmpty_ShouldThrowNewRuntimeexception() {
        Accounts account = new Accounts("0123456", "", "null", "null", role, status);
        RuntimeException expected = Assertions.assertThrows(RuntimeException.class,
                () -> accountsServiceImpl.insertAccounts(account));
        Assertions.assertEquals("Missing Information.", expected.getMessage());
    }
    @Test
    void testInsertAccounts_WhenPasswordEmpty_ShouldThrowNewRuntimeexception() {
        Accounts account = new Accounts("0123456", "lklklk", "kkjkj", "", role, status);
        RuntimeException expected = Assertions.assertThrows(RuntimeException.class,
                () -> accountsServiceImpl.insertAccounts(account));
        Assertions.assertEquals("Missing Information.", expected.getMessage());
    }
    @Test
    void testInsertAccounts_WhenPhonenumberEmpty_ShouldThrowNewRuntimeexception() {
        Accounts account = new Accounts("", "adsasds", "null", "null", role, status);
        RuntimeException expected = Assertions.assertThrows(RuntimeException.class,
                () -> accountsServiceImpl.insertAccounts(account));
        Assertions.assertEquals("Missing Information.", expected.getMessage());
    }
    @Test
    void testInsertAccounts_WhenPhonenumberNotValid_ShouldThrowNewRuntimeexception() {
        Accounts account = new Accounts("12345", "adsasds", "null", "null", role, status);
        RuntimeException expected = Assertions.assertThrows(RuntimeException.class,
                () -> accountsServiceImpl.insertAccounts(account));
        Assertions.assertEquals("Phone Number Not Valid Format.", expected.getMessage());
    }

    @Test
    void testInsertAccounts_WhenFullNameNull_ShouldThrowNewRuntimeexception() {
        Accounts account = new Accounts("0998779755", null, "null", "null", role, status);
        RuntimeException expected = Assertions.assertThrows(RuntimeException.class,
                () -> accountsServiceImpl.insertAccounts(account));
        Assertions.assertEquals("Missing Information.", expected.getMessage());
    }
    @Test
    void testInsertAccounts_WhenUsernameNull_ShouldThrowNewRuntimeexception() {
        Accounts account = new Accounts(null, "adsasds", null, "null", role, status);
        RuntimeException expected = Assertions.assertThrows(RuntimeException.class,
                () -> accountsServiceImpl.insertAccounts(account));
        Assertions.assertEquals("Missing Information.", expected.getMessage());
    }
    @Test
    void testInsertAccounts_WhenPasswordNull_ShouldThrowNewRuntimeexception() {
        Accounts account = new Accounts(null, "adsasds", "null", null, role, status);
        RuntimeException expected = Assertions.assertThrows(RuntimeException.class,
                () -> accountsServiceImpl.insertAccounts(account));
        Assertions.assertEquals("Missing Information.", expected.getMessage());
    }
    @Test
    void testInsertAccounts_WhenPhonenumberNull_ShouldThrowNewRuntimeexception() {
        Accounts account = new Accounts(null, "adsasds", "null", "null", role, status);
        RuntimeException expected = Assertions.assertThrows(RuntimeException.class,
                () -> accountsServiceImpl.insertAccounts(account));
        Assertions.assertEquals("Missing Information.", expected.getMessage());
    }

    @Test
    void testUpdateAccountInformation_WhenAccountNull_ShouldThrowNewRuntimeexception() {
        Accounts account = new Accounts(null, "adsasds", "null", "null", role, status);
        when(accountRepositories.findByPhoneNumber(account.getPhoneNumber())).thenReturn(null);
        RuntimeException expected = Assertions.assertThrows(RuntimeException.class,
                () -> accountsServiceImpl.updateAccountInformation(account));
        Assertions.assertEquals("Some How This Account Is Null.", expected.getMessage());
    }
    @Test
    void testUpdateAccountInformation_WhenMissingSetFullname_ShouldReturnAccount() {
        Accounts initAccount = mock(Accounts.class);
        Accounts expecAccount = mock(Accounts.class);
        when(accountRepositories.findByPhoneNumber(initAccount.getPhoneNumber())).thenReturn(initAccount);
        when(accountRepositories.save(initAccount)).thenReturn(expecAccount);
        Accounts result = accountsServiceImpl.updateAccountInformation(initAccount);
        verify(initAccount).setFullName(initAccount.getFullName());
        verify(initAccount).setAvatar(initAccount.getAvatar());
        verify(initAccount).setUsername(initAccount.getUsername());
        assertThat(result,is(expecAccount));
    }

    @Test
    void testUpdateAccountRole_WhenRoleValueLargeThan2_ShouldThrowRuntimeexception() {
        Accounts account = new Accounts("0123456", "adsasds", "null", "null", role, status);
        int roleValue = 3;
        RuntimeException expected = Assertions.assertThrows(RuntimeException.class, () -> accountsServiceImpl.updateAccountRole(account,roleValue));
        Assertions.assertEquals("Role Not Valid.", expected.getMessage());
    }
    @Test
    void testUpdateAccountRole_WhenRoleValueSmallerThan1_ShouldThrowRuntimeexception() {
        Accounts account = new Accounts("0123456", "adsasds", "null", "null", role, status);
        int roleValue = 0;
        RuntimeException expected = Assertions.assertThrows(RuntimeException.class, () -> accountsServiceImpl.updateAccountRole(account,roleValue));
        Assertions.assertEquals("Role Not Valid.", expected.getMessage());
    }
    @Test
    void testUpdateAccountRole_WhenRoleAccountNull_ShouldThrowRuntimeexception() {
        Accounts account = new Accounts("0123456", "adsasds", "null", "null", role, status);
        int roleValue = 1;
        when(accountRepositories.findByPhoneNumber("0123456")).thenReturn(null);
        RuntimeException expected = Assertions.assertThrows(RuntimeException.class, () -> accountsServiceImpl.updateAccountRole(account,roleValue));
        Assertions.assertEquals("Some How This Account Is Null.", expected.getMessage());
    }
    @Test
    void testUpdateAccountRole_WhenDataValid_ShouldReturnAccountWithRoleAdmin() {
        Mockito.verify(role).setRole(RoleEnum.ROLE_ADMIN.name());
        Roles userRoles = new Roles(RoleEnum.ROLE_USER.name());
        Roles adminRole = roleCaptor.getValue();
        Accounts initAccount = new Accounts("0123456", "null", "null", "null", userRoles, status);
        Accounts expecAccount = new Accounts("0123456", "null", "null", "null", adminRole, status);
        int roleValue = 1;
        when(accountRepositories.findByPhoneNumber(initAccount.getPhoneNumber())).thenReturn(initAccount);
        when(accountRepositories.save(initAccount)).thenReturn(expecAccount);
        Accounts result = accountsServiceImpl.updateAccountRole(initAccount, roleValue);
        verify(initAccount).setRole(adminRole);
        Assertions.assertEquals(result.getRole(), adminRole);
    }



    @Test
    void testUpdateAccountStatus() {

    }
}
