package com.nash.assignment.services;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nash.assignment.constant.StatusEnum;
import com.nash.assignment.dto.AccountDto;
import com.nash.assignment.dto.AccountLoginDto;
import com.nash.assignment.exceptions.InformationNotValidException;
import com.nash.assignment.modal.Account;
import com.nash.assignment.repositories.AccountRepositories;

@Service
public class LoginServiceImpl {

    @Autowired
    JwtServiceImpl jwtServiceImpl;
    @Autowired
    AccountRepositories accountRepositories;
    @Autowired
    UserDetailServiceImpl userDetailServiceImpl;
    @Autowired
    HttpServletRequest request;
    private final PasswordEncoder passwordEncoder;

    public LoginServiceImpl(JwtServiceImpl jwtServiceImpl, AccountRepositories accountRepositories,
            UserDetailServiceImpl userDetailServiceImpl, PasswordEncoder passwordEncoder) {
        this.jwtServiceImpl = jwtServiceImpl;
        this.accountRepositories = accountRepositories;
        this.userDetailServiceImpl = userDetailServiceImpl;
        this.passwordEncoder = passwordEncoder;
    }

    public Map<String, String> login(AccountLoginDto accountDto) {
        Map<String, String> result = new HashMap<>();
        HttpSession session = request.getSession();
        String email = accountDto.getEmail();
        Account account = accountRepositories.findByEmail(email);
        if (StatusEnum.DEACTIVE.equals(account.getStatus())) {
            throw new InformationNotValidException("Your Account Have Been Block");
        }
        if (!passwordEncoder.matches(accountDto.getPassword(), account.getPassword())) {
            throw new InformationNotValidException("Invalid Email Or Password");
        }
        session.setAttribute("email", email);
        UserDetails user = userDetailServiceImpl.loadUserByUsername(email);
        String accessToken = jwtServiceImpl.createAccessToken(user);
        String refeshToken = jwtServiceImpl.createRefreshToken(user);
        result.put("accessToken", accessToken);
        result.put("refeshToken", refeshToken);
        return result;

    }
}
