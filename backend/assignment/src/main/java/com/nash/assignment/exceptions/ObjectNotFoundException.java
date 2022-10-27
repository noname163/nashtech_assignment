package com.nash.assignment.exceptions;

public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException() {
        super();
    }

    public ObjectNotFoundException(String mess) {
        super(mess);
    }

    public ObjectNotFoundException(String mess, Throwable cause) {
        super(mess, cause);
    }
}
