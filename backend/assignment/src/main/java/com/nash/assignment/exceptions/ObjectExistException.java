package com.nash.assignment.exceptions;

public class ObjectExistException extends RuntimeException {

    public ObjectExistException() {
        super();
    }

    public ObjectExistException(String mess) {
        super(mess);
    }

    public ObjectExistException(String mess, Throwable cause) {
        super(mess, cause);
    }
}
