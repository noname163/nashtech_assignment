package com.nash.assignment.exceptions;

public class JwtNotValidException extends RuntimeException{

    public JwtNotValidException() {
    }

    public JwtNotValidException(String message) {
        super(message);
    }

    public JwtNotValidException(Throwable cause) {
        super(cause);
    }

    public JwtNotValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtNotValidException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
