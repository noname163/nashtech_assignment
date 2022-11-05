package com.nash.assignment.filter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nash.assignment.dto.response.ExceptionDto;
import com.nash.assignment.exceptions.handlers.ExceptionHander;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            responseError(HttpStatus.INTERNAL_SERVER_ERROR, response, ex);
        }
    }

    public void responseError(HttpStatus status, HttpServletResponse response, Throwable exception) {
        response.setStatus(status.value());
        response.setContentType("application/json");
        ExceptionDto exceptionDto = null;
        ExceptionHander exceptionHander = new ExceptionHander();
        switch(status.value()){
            case 400:
                exceptionDto = exceptionHander.handleValidationException(exception.getMessage());
            case 404:
                exceptionDto = exceptionHander.NotFoundException(exception.getMessage());
            default:
                exceptionDto = exceptionHander.handleException(exception.getMessage());
        }
        if(exception.getClass()==TokenExpiredException.class){
            exceptionDto = exceptionHander.handleJwtTokenExpiredException(exception.getMessage());
        }
        
        try {
            String json = convertObjectToJson(exceptionDto);
            response.getWriter().write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
