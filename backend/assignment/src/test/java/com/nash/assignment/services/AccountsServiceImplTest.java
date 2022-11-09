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
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nash.assignment.constant.UserRole;
import com.nash.assignment.constant.AccountStatus;
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

    private AccountStatus status;

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
    void getAccountById_WhenAccountNull_ShouldThrowObjectNotFoundException() {
        Account account = mock(Account.class);
        when(accountRepositories.findById(account.getId())).thenReturn(Optional.empty());

        ObjectNotFoundException expected = Assertions.assertThrows(ObjectNotFoundException.class,
                () -> accountsServiceImpl.getAccountById(account.getId()));

        assertThat("Cannot Find Account With ID: " + account.getId(), is(expected.getMessage()));

    }

    @Test
    void getAccountById_WhenAccountNotNull_ShouldReturnAccountObject() {
        Account account = new Account();
        account.setId(1l);
        AccountDto expected = mock(AccountDto.class);

        when(accountRepositories.findById(1L)).thenReturn(Optional.of(account));
        when(modelMapper.map(account, AccountDto.class)).thenReturn(expected);

        AccountDto actual = accountsServiceImpl.getAccountById(1l);

        assertThat(actual, is(expected));

    }

    @Test
    void insertAccounts_WhenAccountPhoneNumberExist_ShouldThrowInformationNotValid() {
        AccountDto accountDto = new AccountDto();
        accountDto.setPhoneNumber("01234");
        when(accountRepositories.findByPhoneNumber("01234")).thenReturn(account);
        when(rolesRepositories.findByRole(UserRole.ROLE_USER)).thenReturn(role);
        InformationNotValidException actual = Assertions.assertThrows(InformationNotValidException.class,
                () -> accountsServiceImpl.insertAccounts(accountDto));
        assertEquals("This Phonenumber Already Exist.", actual.getMessage());
    }
    @Test
    void insertAccounts_WhenEmailExist_ShouldThrowInformationNotValid() {
        AccountDto accountDto = new AccountDto();
        accountDto.setPhoneNumber("01234");
        accountDto.setEmail("test@gmail.com");
        when(accountRepositories.findByPhoneNumber("01234")).thenReturn(null);
        when(accountRepositories.findByEmail("test@gmail.com")).thenReturn(account);
        when(rolesRepositories.findByRole(UserRole.ROLE_USER)).thenReturn(role);
        InformationNotValidException actual = Assertions.assertThrows(InformationNotValidException.class,
                () -> accountsServiceImpl.insertAccounts(accountDto));
        assertEquals("This Email Already Exist.", actual.getMessage());
    }

    @Test
    void insertAccounts_WhenAccountDataValid_Should_SetRole_SetStatus_SetPassword_Save() {
        Account expecAccount = mock(Account.class);
        AccountDto initAccount = mock(AccountDto.class);

        when(accountRepositories.findByPhoneNumber(initAccount.getPhoneNumber())).thenReturn(null);
        when(accountRepositories.findByEmail(initAccount.getEmail())).thenReturn(null);
        when(rolesRepositories.findByRole(UserRole.ROLE_USER)).thenReturn(role);
        when(modelMapper.map(initAccount, Account.class)).thenReturn(expecAccount);
        when(accountRepositories.save(expecAccount)).thenReturn(expecAccount);
        when(modelMapper.map(expecAccount, AccountDto.class)).thenReturn(initAccount);

        AccountDto actual = accountsServiceImpl.insertAccounts(initAccount);

        verify(initAccount).setRole(role);
        verify(initAccount).setStatus(AccountStatus.ACTIVE);
        verify(initAccount).setPassword(passwordEncoder.encode(initAccount.getPassword()));
        verify(accountRepositories).save(expecAccount);
        assertThat(actual, is(initAccount));
    }
    @Test
    void insertAccounts_WhenAccountDataValid_ShouldReturnAccountDto() {
        Account expecAccount = new Account();
        AccountDto initAccount = new AccountDto();
        initAccount.setPhoneNumber("0938577332");
        initAccount.setEmail("test@gmail.com");

        when(accountRepositories.findByPhoneNumber("0938577332")).thenReturn(null);
        when(accountRepositories.findByEmail("test@gmail.com")).thenReturn(null);
        when(rolesRepositories.findByRole(UserRole.ROLE_USER)).thenReturn(role);
        when(modelMapper.map(initAccount, Account.class)).thenReturn(expecAccount);
        when(accountRepositories.save(expecAccount)).thenReturn(expecAccount);
        when(modelMapper.map(expecAccount, AccountDto.class)).thenReturn(initAccount);

        AccountDto actual = accountsServiceImpl.insertAccounts(initAccount);

        assertThat(actual, is(initAccount));
    }

    @Test
    void getAllAccounts_WhenAccountNull_ShouldThrowObjectNotFoundException() {
        when(accountRepositories.findAll()).thenReturn(null);
        ObjectNotFoundException actual = Assertions.assertThrows(ObjectNotFoundException.class,
                () -> accountsServiceImpl.getAllAccounts());
        assertThat("Account List Is Empty.", is(actual.getMessage()));
    }

    @Test
    void getAllAccounts_WhenAccountEmpty_ShouldThrowObjectNotFoundException() {
        List<Account> list = new ArrayList();
        when(accountRepositories.findAll()).thenReturn(list);
        ObjectNotFoundException actual = Assertions.assertThrows(ObjectNotFoundException.class,
                () -> accountsServiceImpl.getAllAccounts());
        assertThat("Account List Is Empty.", is(actual.getMessage()));
    }

    @Test
    void getAllAccounts_WhenDataValid_ShouldReturnListOfAccountDto() {
        List<Account> accountList = new ArrayList<>();
        List<AccountDto> expected = new ArrayList<>();
        expected.add(accountDto);
        accountList.add(account);
        when(accountRepositories.findAll()).thenReturn(accountList);
        when(modelMapper.map(account, AccountDto.class)).thenReturn(accountDto);

        List<AccountDto> actual = accountsServiceImpl.getAllAccounts();
        assertThat(expected, is(actual));
    }

    @Test
    void updateAccountInformation_WhenAccountNull_ShouldThrowNewObjectNotFoundException() {
        when(accountRepositories.findByPhoneNumber(accountDto.getPhoneNumber())).thenReturn(null);
        ObjectNotFoundException actual = Assertions.assertThrows(ObjectNotFoundException.class,
                () -> accountsServiceImpl.updateAccountInformation(accountDto));
        assertThat("Cannot Find Account With Phonenumber: " + accountDto.getPhoneNumber(), is(actual.getMessage()));
    }

    @Test
    void updateAccountInformation_WhenAccountDataValid_ShouldReturnAccountDtoObject() {

        when(accountRepositories.findByPhoneNumber(accountDto.getPhoneNumber())).thenReturn(account);
        when(accountRepositories.save(account)).thenReturn(account);
        when(modelMapper.map(account, AccountDto.class)).thenReturn(accountDto);

        AccountDto actual = accountsServiceImpl.updateAccountInformation(accountDto);

        verify(account).setFullName(accountDto.getFullName());
        verify(account).setImage(accountDto.getImage());

        assertThat(accountDto, is(actual));
    }

    @Test
    void updateAccountRole_WhenRoleNull_ShouldThrowInfomationNotValidException() {
        when(accountRepositories.findById(account.getId())).thenReturn(Optional.of(account));
        when(rolesRepositories.findByRole(UserRole.ROLE_ADMIN)).thenReturn(null);

        ObjectNotFoundException actual = Assertions.assertThrows(ObjectNotFoundException.class,
                () -> accountsServiceImpl.updateAccountRole(account.getId()));

        assertThat("Cannot Found Role: " + UserRole.ROLE_ADMIN.toString(), is(actual.getMessage()));

    }

    @Test
    void updateAccountRole_WhenAccountNull_ShouldThrowObjectNotException() {
        when(accountRepositories.findById(account.getId())).thenReturn(Optional.empty());

        ObjectNotFoundException actual = Assertions.assertThrows(ObjectNotFoundException.class,
                () -> accountsServiceImpl.updateAccountRole(account.getId()));

        assertThat("Cannot Found Account With Id: " + account.getId(), is(actual.getMessage()));

    }

    @Test
    void updateAccountRole_WhenDataValid_ShouldReturnAccountDtoWithRoleAdmin() {
        Role adminRole = new Role();
        adminRole.setRole(UserRole.ROLE_ADMIN);
        Role userRole = new Role();
        userRole.setRole(UserRole.ROLE_USER);
        Account accountinit = new Account();
        AccountDto accountExpect = new AccountDto();
        accountinit.setId(1);
        accountinit.setRole(userRole);
        accountExpect.setId(1);
        accountExpect.setRole(adminRole);

        when(accountRepositories.findById(1l)).thenReturn(Optional.of(accountinit));
        when(rolesRepositories.findByRole(UserRole.ROLE_ADMIN)).thenReturn(adminRole);
        when(modelMapper.map(accountinit, AccountDto.class)).thenReturn(accountExpect);

        AccountDto actual = accountsServiceImpl.updateAccountRole(1l);
        
        verify(accountRepositories).save(accountinit);
        assertThat(actual.getRole().getRole(), is(UserRole.ROLE_ADMIN));

    }

    @Test
    void updateAccountStatus_WhenAccountNull_ShouldThrowObjectNotFound() {
        when(accountRepositories.findById(accountDto.getId())).thenReturn(Optional.empty());
        ObjectNotFoundException actual = Assertions.assertThrows(ObjectNotFoundException.class,
                () -> accountsServiceImpl.updateAccountStatus(accountDto.getId(), AccountStatus.ACTIVE));
        assertThat("Cannot Find Account With Id: " + accountDto.getId(), is(actual.getMessage()));
    }

    @Test
    void updateAccountStatus_WhenDataValid_ShouldReturnActiveStatus() {
        Account accountinit = new Account();
        AccountDto accountExpect = new AccountDto();
        accountinit.setId(1);
        accountinit.setStatus(AccountStatus.DEACTIVE);
        accountExpect.setId(1);
        accountExpect.setStatus(AccountStatus.ACTIVE);

        when(accountRepositories.findById(1l)).thenReturn(Optional.of(accountinit));
        when(modelMapper.map(accountinit, AccountDto.class)).thenReturn(accountExpect);
        AccountDto actual = accountsServiceImpl.updateAccountStatus(accountinit.getId(), AccountStatus.ACTIVE);

        verify(accountRepositories).save(accountinit);
        assertThat(actual.getStatus(), is(AccountStatus.ACTIVE));
    }

    @Test
    void updateAccountStatus_WhenDataValid_ShouldReturnDeActiveStatus() {
        Account accountinit = new Account();
        AccountDto accountExpect = new AccountDto();
        accountinit.setId(1);
        accountinit.setStatus(AccountStatus.ACTIVE);
        accountExpect.setId(1);
        accountExpect.setStatus(AccountStatus.DEACTIVE);

        when(accountRepositories.findById(1l)).thenReturn(Optional.of(accountinit));
        when(modelMapper.map(accountinit, AccountDto.class)).thenReturn(accountExpect);

        AccountDto actual = accountsServiceImpl.updateAccountStatus(1l, AccountStatus.DEACTIVE);
        verify(accountRepositories).save(accountinit);

        assertThat(actual.getStatus(), is(AccountStatus.DEACTIVE));
    }

    @Test
    void getAccountByPhonenumber_WhenAccountNull_ShouldThrowObjectNotFoundException(){
        when(accountRepositories.findByPhoneNumber("012234565656")).thenReturn(null);
        ObjectNotFoundException actual = Assertions.assertThrows(ObjectNotFoundException.class,()-> accountsServiceImpl.getAccountByPhonenumber("012234565656"));
        assertThat(actual.getMessage(), is("Cannot Find Account With Phone: " + "012234565656"));
    }
    @Test
    void getAccountByPhonenumber_WhenDataValid_ShouldReturnAccountDtoObject(){
        when(accountRepositories.findByPhoneNumber("012234565656")).thenReturn(account);
        when(modelMapper.map(account, AccountDto.class)).thenReturn(accountDto);
        AccountDto actual = accountsServiceImpl.getAccountByPhonenumber("012234565656");
        assertThat(actual, is(accountDto));
    }
}
