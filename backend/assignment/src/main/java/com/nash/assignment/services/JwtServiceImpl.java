package com.nash.assignment.services;

import java.sql.Date;
import java.util.stream.Collectors;

import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class JwtServiceImpl {

    private Environment env;
    
    public JwtServiceImpl(Environment env) {
        this.env = env;
    }

    public String createAccessToken(UserDetails userDetails) {
        String secretKey =  env.getProperty("jwt.secret.key");
        Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
        return JWT.create().withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withClaim("Roles",
                        userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .sign(algorithm);
    }

    public String createRefreshToken(UserDetails userDetails) {
        String secretKey =  env.getProperty("jwt.secret.key");
        Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
        return JWT.create().withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .sign(algorithm);
    }

}
