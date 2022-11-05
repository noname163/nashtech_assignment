package com.nash.assignment.exceptions.handlers;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.nash.assignment.dto.response.ExceptionDto;

import java.net.BindException;

@RestControllerAdvice

public class ExceptionHander {


    @ExceptionHandler(TokenExpiredException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ExceptionDto handleJwtTokenExpiredException(String message){
        ExceptionDto exceptionDto = new ExceptionDto(401, "Unauthorized", message);
        return exceptionDto;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ExceptionDto handleValidationException(String message){
        ExceptionDto exceptionDto = new ExceptionDto(400, "BAD_REQUEST", message);
        return exceptionDto;
    }
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ExceptionDto NotFoundException(String message){
        ExceptionDto exceptionDto = new ExceptionDto(404, "NOT_FOUND", message);
        return exceptionDto;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionDto handleException(String message){
        ExceptionDto exceptionDto = new ExceptionDto(500, "INTERNAL_SERVER_ERROR", message);
        return exceptionDto;
    }

}
