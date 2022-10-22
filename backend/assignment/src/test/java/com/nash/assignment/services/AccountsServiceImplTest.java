package com.nash.assignment.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nash.assignment.constant.RoleEnum;
import com.nash.assignment.constant.StatusEnum;
import com.nash.assignment.dto.AccountDto;
import com.nash.assignment.exceptions.InformationNotValidException;
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
    Account account;
    AccountDto accountDto;

    private StatusEnum status;

    @BeforeEach
    void setUpBefore() {

        rolesRepositories = mock(RolesRepositories.class);
        role = mock(Role.class);
        modelMapper = mock(ModelMapper.class);
        accountDto = mock(AccountDto.class);
        account = mock(Account.class);
        passwordEncoder = mock(PasswordEncoder.class);
        accountRepositories = mock(AccountRepositories.class);
        accountsServiceImpl = new AccountsServiceImpl(accountRepositories, rolesRepositories,
                passwordEncoder, modelMapper);

    }

    @Test
    void GetAccountById_WhenAccountNull_ShouldThrowObjectNotFoundException() {
        Account account = mock(Account.class);
        when(accountRepositories.findById(account.getId())).thenReturn(Optional.empty());

        ObjectNotFoundException expected = Assertions.assertThrows(ObjectNotFoundException.class,
                () -> accountsServiceImpl.getAccountById(account.getId()));

        assertThat("Cannot Find Account With ID: " + account.getId(), is(expected.getMessage()));

    }

    @Test
    void GetAccountById_WhenAccountNotNull_ShouldReturnAccountObject() {
        Account account = mock(Account.class);
        AccountDto accountDto = mock(AccountDto.class);

        when(accountRepositories.findById(0L)).thenReturn(Optional.of(account));
        when(modelMapper.map(account, AccountDto.class)).thenReturn(accountDto);

        AccountDto expected = accountsServiceImpl.getAccountById(account.getId());

        assertThat(accountDto, is(expected));

    }

    @Test
    void InsertAccounts_WhenAccountNotNull_ShouldThrowInformationNotValid() {
        when(accountRepositories.findByPhoneNumber(account.getPhoneNumber())).thenReturn(account);
        when(rolesRepositories.findByRole(RoleEnum.ROLE_USER)).thenReturn(role);
        InformationNotValidException actual = Assertions.assertThrows(InformationNotValidException.class,
                () -> accountsServiceImpl.insertAccounts(accountDto));
        assertEquals("This Phonenumber Already Exist.", actual.getMessage());
    }

    @Test
    void InsertAccounts_WhenAccountDataValid_ShouldReturnAccountDto() {
        Account expecAccount = mock(Account.class);
        AccountDto initAccount = mock(AccountDto.class);

        when(accountRepositories.findByPhoneNumber(initAccount.getPhoneNumber())).thenReturn(null);
        when(rolesRepositories.findByRole(RoleEnum.ROLE_USER)).thenReturn(role);
        when(modelMapper.map(initAccount, Account.class)).thenReturn(expecAccount);
        when(accountRepositories.save(expecAccount)).thenReturn(expecAccount);
        when(modelMapper.map(expecAccount, AccountDto.class)).thenReturn(initAccount);
        AccountDto actual = accountsServiceImpl.insertAccounts(initAccount);

        verify(initAccount).setRole(role);
        verify(initAccount).setStatus(StatusEnum.ACTIVE);
        verify(initAccount).setPassword(passwordEncoder.encode(initAccount.getPassword()));
        verify(accountRepositories).save(expecAccount);
        assertThat(actual, is(initAccount));
    }

    @Test
    void GetAllAccounts_WhenAccountNull_ShouldThrowObjectNotFoundException() {
        when(accountRepositories.findAll()).thenReturn(null);
        ObjectNotFoundException actual = Assertions.assertThrows(ObjectNotFoundException.class,
                ()->accountsServiceImpl.getAllAccounts());
        assertThat("Account List Is Empty.", is(actual.getMessage()));
    }

    @Test
    void GetAllAccounts_WhenAccountEmpty_ShouldThrowObjectNotFoundException() {
        List<Account> list = new ArrayList();
        when(accountRepositories.findAll()).thenReturn(list);
        ObjectNotFoundException actual = Assertions.assertThrows(ObjectNotFoundException.class,
                () -> accountsServiceImpl.getAllAccounts());
        assertThat("Account List Is Empty.", is(actual.getMessage()));
    }

    @Test
    void GetAllAccounts_WhenDataValid_ShouldReturnListOfAccount() {
        List<Account> expected = new ArrayList<>();
        expected.add(account);
        when(accountRepositories.findAll()).thenReturn(expected);
        List<Account> actual = accountsServiceImpl.getAllAccounts();
        assertThat(expected, is(actual));
    }

    @Test
    void UpdateAccountInformation_WhenAccountNull_ShouldThrowNewObjectNotFoundException() {
        when(accountRepositories.findByPhoneNumber(accountDto.getPhoneNumber())).thenReturn(null);
        ObjectNotFoundException actual = Assertions.assertThrows(ObjectNotFoundException.class,
                () -> accountsServiceImpl.updateAccountInformation(accountDto));
        assertThat("Cannot Find Account With Phonenumber: " + accountDto.getPhoneNumber(), is(actual.getMessage()));
    }

    @Test
    void UpdateAccountInformation_WhenAccountDataValid_ShouldReturnAccountDtoObject() {

        when(accountRepositories.findByPhoneNumber(accountDto.getPhoneNumber())).thenReturn(account);
        when(accountRepositories.save(account)).thenReturn(account);
        when(modelMapper.map(account, AccountDto.class)).thenReturn(accountDto);

        AccountDto actual = accountsServiceImpl.updateAccountInformation(accountDto);

        verify(account).setFullName(accountDto.getFullName());
        verify(account).setUsername(accountDto.getUsername());
        verify(account).setImage(accountDto.getImage());

        assertThat(accountDto, is(actual));
    }

    @Test
    void UpdateAccountRole_WhenRoleValue_0_ShouldThrowInfomationNotValidException() {
        when(accountRepositories.findById(account.getId())).thenReturn(Optional.of(account));

        InformationNotValidException actual = Assertions.assertThrows(InformationNotValidException.class, 
                ()->accountsServiceImpl.updateAccountRole(account.getId(), 0));
        
        assertThat("Role Not Valid", is(actual.getMessage()));

    }

    @Test
    void UpdateAccountRole_WhenRoleValue_3_ShouldThrowInfomationNotValidException() {
        when(accountRepositories.findById(account.getId())).thenReturn(Optional.of(account));

        InformationNotValidException actual = Assertions.assertThrows(InformationNotValidException.class, 
                ()->accountsServiceImpl.updateAccountRole(account.getId(), 3));
        
        assertThat("Role Not Valid", is(actual.getMessage()));

    }

    @Test
    void UpdateAccountRole_WhenAccountNull_ShouldThrowObjectNotException() {
        when(accountRepositories.findById(account.getId())).thenReturn(Optional.empty());

        ObjectNotFoundException actual = Assertions.assertThrows(ObjectNotFoundException.class, 
                ()->accountsServiceImpl.updateAccountRole(account.getId(), 1));
        
        assertThat("Cannot Found Account With Id: " + account.getId(), is(actual.getMessage()));

    }

    @Test
    void UpdateAccountRole_WhenDataValid_ShouldReturnAccountDtoWithRoleUser() {
        
        when(accountRepositories.findById(account.getId())).thenReturn(Optional.of(account));
        when(rolesRepositories.findByRole(RoleEnum.ROLE_USER)).thenReturn(role);
        when(accountRepositories.save(account)).thenReturn(account);
        when(modelMapper.map(account, AccountDto.class)).thenReturn(accountDto);
        ArgumentCaptor<Role> roleInsert = ArgumentCaptor.forClass(Role.class);

        AccountDto actual = accountsServiceImpl.updateAccountRole(account.getId(), 1);
        verify(account).setRole(roleInsert.capture());
        assertThat(accountDto.getRole(), is(actual.getRole()));

    }

    @Test
    void UpdateAccountRole_WhenDataValid_ShouldReturnAccountDtoWithRoleAdmin() {
        
        when(accountRepositories.findById(account.getId())).thenReturn(Optional.of(account));
        when(rolesRepositories.findByRole(RoleEnum.ROLE_ADMIN)).thenReturn(role);
        when(accountRepositories.save(account)).thenReturn(account);
        when(modelMapper.map(account, AccountDto.class)).thenReturn(accountDto);
        ArgumentCaptor<Role> roleInsert = ArgumentCaptor.forClass(Role.class);

        AccountDto actual = accountsServiceImpl.updateAccountRole(account.getId(), 2);
        verify(account).setRole(roleInsert.capture());
        assertThat(accountDto.getRole(), is(actual.getRole()));

    }

    @Test
    void UpdateAccountStatus_WhenStatusValue_0_ShouldThrowInformationNotValid(){
        when(accountRepositories.findById(accountDto.getId())).thenReturn(Optional.of(account));
        InformationNotValidException actual = Assertions.assertThrows(InformationNotValidException.class, 
                ()-> accountsServiceImpl.updateAccountStatus(accountDto.getId(), 0));
        assertThat("Status Not Valid.", is(actual.getMessage()));
    }

    @Test
    void UpdateAccountStatus_WhenStatusValue_3_ShouldThrowInformationNotValid(){
        when(accountRepositories.findById(accountDto.getId())).thenReturn(Optional.of(account));
        InformationNotValidException actual = Assertions.assertThrows(InformationNotValidException.class, 
                ()-> accountsServiceImpl.updateAccountStatus(accountDto.getId(), 0));
        assertThat("Status Not Valid.", is(actual.getMessage()));
    }

    @Test
    void UpdateAccountStatus_WhenAccountNull_ShouldThrowObjectNotFound(){
        when(accountRepositories.findById(accountDto.getId())).thenReturn(Optional.empty());
        ObjectNotFoundException actual = Assertions.assertThrows(ObjectNotFoundException.class, 
                ()-> accountsServiceImpl.updateAccountStatus(accountDto.getId(), 1));
        assertThat("Cannot Find Account With Id: " + accountDto.getId(), is(actual.getMessage()));
    }

    @Test
    void UpdateAccountStatus_WhenDataValid_ShouldReturnActiveStatus() {
        when(accountRepositories.findById(account.getId())).thenReturn(Optional.of(account));
        when(accountRepositories.save(account)).thenReturn(account);
        when(modelMapper.map(account, AccountDto.class)).thenReturn(accountDto);
        ArgumentCaptor<StatusEnum> status = ArgumentCaptor.forClass(StatusEnum.class);
        AccountDto actual = accountsServiceImpl.updateAccountStatus(account.getId(), 1);

        verify(account).setStatus(status.capture());
        assertThat(accountDto.getStatus(), is(actual.getStatus()));
    }

    @Test
    void UpdateAccountStatus_WhenDataValid_ShouldReturnDeActiveStatus() {
        
        when(accountRepositories.findById(account.getId())).thenReturn(Optional.of(account));
        when(accountRepositories.save(account)).thenReturn(account);
        when(modelMapper.map(account, AccountDto.class)).thenReturn(accountDto);

        ArgumentCaptor<StatusEnum> status = ArgumentCaptor.forClass(StatusEnum.class);
        AccountDto actual = accountsServiceImpl.updateAccountStatus(account.getId(), 2);

        verify(account).setStatus(status.capture());
        assertThat(accountDto.getStatus(), is(actual.getStatus()));
    }
}
