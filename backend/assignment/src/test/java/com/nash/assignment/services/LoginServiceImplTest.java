package com.nash.assignment.services;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nash.assignment.constant.AccountStatus;
import com.nash.assignment.dto.AccountDto;
import com.nash.assignment.dto.AccountLoginDto;
import com.nash.assignment.exceptions.InformationNotValidException;
import com.nash.assignment.exceptions.ObjectNotFoundException;
import com.nash.assignment.modal.Account;
import com.nash.assignment.repositories.AccountRepositories;

public class LoginServiceImplTest {

    
    JwtServiceImpl jwtServiceImpl;
    
    AccountRepositories accountRepositories;
    
    UserDetailServiceImpl userDetailServiceImpl;
    
    HttpServletRequest request;

    LoginServiceImpl loginServiceImpl;

    PasswordEncoder passwordEncoder;

    AccountLoginDto accountDto;

    HttpSession session;

    Account account;

    @BeforeEach
    void setUp(){
        accountDto = new AccountLoginDto();
        account = mock(Account.class);
        session = mock(HttpSession.class);
        jwtServiceImpl = mock(JwtServiceImpl.class);
        accountRepositories = mock(AccountRepositories.class);
        userDetailServiceImpl = mock(UserDetailServiceImpl.class);
        request = mock(HttpServletRequest.class);
        passwordEncoder = mock(PasswordEncoder.class);
        loginServiceImpl = new LoginServiceImpl(jwtServiceImpl, accountRepositories, userDetailServiceImpl, passwordEncoder, request);
    }
    @Test
    void login_WhenAccountNull_ShouldThrow_ObjectNotFoundException() {
        accountDto.setEmail("test@gmail.com");
        when(request.getSession()).thenReturn(session);
        when(accountRepositories.findByEmail("test@gmail.com")).thenReturn(null);
        ObjectNotFoundException actual = Assertions.assertThrows(ObjectNotFoundException.class,()-> loginServiceImpl.login(accountDto));
        assertThat(actual.getMessage(), is("Invalid Email Or Password"));

    }

    @Test
    void login_WhenAccountWasBan_ShouldThrowInValidInformationException() {
        accountDto.setEmail("test@gmail.com");
        when(request.getSession()).thenReturn(session);
        when(accountRepositories.findByEmail("test@gmail.com")).thenReturn(account);
        when(account.getStatus()).thenReturn(AccountStatus.DEACTIVE);
        InformationNotValidException actual = Assertions.assertThrows(InformationNotValidException.class,()-> loginServiceImpl.login(accountDto));
        assertThat(actual.getMessage(), is("Your Account Have Been Block"));

    }
    @Test
    void login_WhenWrongPassword_ShouldThrowInValidInformationException() {
        accountDto.setEmail("test@gmail.com");
        when(request.getSession()).thenReturn(session);
        when(accountRepositories.findByEmail("test@gmail.com")).thenReturn(account);
        when(account.getStatus()).thenReturn(AccountStatus.ACTIVE);
        when(passwordEncoder.matches(accountDto.getPassword(), account.getPassword())).thenReturn(false);
        InformationNotValidException actual = Assertions.assertThrows(InformationNotValidException.class,()-> loginServiceImpl.login(accountDto));
        assertThat(actual.getMessage(), is("Invalid Email Or Password"));

    }
    @Test
    void login_WhenDataValid_ShouldReturnMapObject() {
        // ArgumentCaptor<Map<String,String>> expected = ArgumentCaptor.forClass(Map.class);
        Map<String,String> expected = new HashMap<>();
        expected.put("accessToken", "test");
        expected.put("refeshToken", "test");
        UserDetails user = mock(UserDetails.class);
        accountDto.setEmail("test@gmail.com");

        when(request.getSession()).thenReturn(session);
        when(accountRepositories.findByEmail("test@gmail.com")).thenReturn(account);
        when(account.getStatus()).thenReturn(AccountStatus.ACTIVE);
        when(passwordEncoder.matches(accountDto.getPassword(), account.getPassword())).thenReturn(true);
        when(userDetailServiceImpl.loadUserByUsername(accountDto.getEmail())).thenReturn(user);
        when(jwtServiceImpl.createAccessToken(user)).thenReturn("test");
        when(jwtServiceImpl.createRefreshToken(user)).thenReturn("test");

        Map<String,String> actual = loginServiceImpl.login(accountDto);
        verify(session).setAttribute("email", accountDto.getEmail());
        assertThat(actual.get("accessToken"), is(expected.get("accessToken")));
        assertThat(actual.get("refeshToken"), is(expected.get("refeshToken")));
    }
}
