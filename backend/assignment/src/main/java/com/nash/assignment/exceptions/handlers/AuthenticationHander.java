package com.nash.assignment.exceptions.handlers;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationHander extends AuthenticationException {

    public AuthenticationHander(String msg) {
        super(msg);
    }

}
