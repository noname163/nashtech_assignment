package com.nash.assignment.exceptions.handlers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.nash.assignment.dto.response.ExceptionDto;
import com.nash.assignment.exceptions.InformationNotValidException;
import com.nimbusds.oauth2.sdk.ErrorResponse;

@ControllerAdvice
public class DataNotValidHander extends ResponseEntityExceptionHandler {

    @ExceptionHandler({InformationNotValidException.class})
    protected ResponseEntity<ExceptionDto> handleIllegalArgumentException(RuntimeException exception,
            WebRequest request) {
        ExceptionDto error = new ExceptionDto("400", "BAD_REQUEST", exception.getMessage());
        return new ResponseEntity<ExceptionDto>(error, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ExceptionDto exception = new ExceptionDto("400", "BAD_REQUEST", errors);

        return new ResponseEntity<Object>(exception, HttpStatus.BAD_REQUEST);
    }

}
