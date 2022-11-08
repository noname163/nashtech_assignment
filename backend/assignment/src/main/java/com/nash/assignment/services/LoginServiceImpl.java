package com.nash.assignment.services;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nash.assignment.constant.AccountStatus;
import com.nash.assignment.dto.AccountLoginDto;
import com.nash.assignment.exceptions.InformationNotValidException;
import com.nash.assignment.exceptions.ObjectNotFoundException;
import com.nash.assignment.modal.Account;
import com.nash.assignment.repositories.AccountRepositories;

@Service
public class LoginServiceImpl {

    
    JwtServiceImpl jwtServiceImpl;
    
    AccountRepositories accountRepositories;
    
    UserDetailServiceImpl userDetailServiceImpl;
    
    HttpServletRequest request;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginServiceImpl(JwtServiceImpl jwtServiceImpl, AccountRepositories accountRepositories,
            UserDetailServiceImpl userDetailServiceImpl, PasswordEncoder passwordEncoder, HttpServletRequest request) {
        this.jwtServiceImpl = jwtServiceImpl;
        this.accountRepositories = accountRepositories;
        this.userDetailServiceImpl = userDetailServiceImpl;
        this.passwordEncoder = passwordEncoder;
        this.request = request;
    }

    public Map<String, String> login(AccountLoginDto accountDto) {
        Map<String, String> result = new HashMap<>();
        HttpSession session = request.getSession();
        Account account = accountRepositories.findByEmail(accountDto.getEmail());
        if(account == null){
            throw new ObjectNotFoundException("Invalid Email Or Password");
        }
        if (AccountStatus.DEACTIVE.equals(account.getStatus())) {
            throw new InformationNotValidException("Your Account Have Been Block");
        }
        if (!passwordEncoder.matches(accountDto.getPassword(), account.getPassword())) {
            throw new InformationNotValidException("Invalid Email Or Password");
        }
        session.setAttribute("email", accountDto.getEmail());
        UserDetails user = userDetailServiceImpl.loadUserByUsername(accountDto.getEmail());
        String accessToken = jwtServiceImpl.createAccessToken(user);
        String refeshToken = jwtServiceImpl.createRefreshToken(user);
        result.put("accessToken", accessToken);
        result.put("refeshToken", refeshToken);
        return result;

    }
}
