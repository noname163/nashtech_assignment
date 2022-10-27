package com.nash.assignment.exceptions;

public class InformationNotValidException extends RuntimeException {

    public InformationNotValidException() {
        super();
    }

    public InformationNotValidException(String mess) {
        super(mess);
    }

    public InformationNotValidException(String mess, Throwable cause) {
        super(mess, cause);
    }
}
