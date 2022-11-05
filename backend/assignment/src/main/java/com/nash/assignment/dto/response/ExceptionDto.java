package com.nash.assignment.dto.response;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ExceptionDto {

    private int code;
    private String status;
    private String message;
    @JsonInclude(Include.NON_NULL)
    private Map<String, String> errors;

    public ExceptionDto(int code, String status, Map<String, String> errors) {
        super();
        this.code = code;
        this.status = status;
        this.errors = errors;
    }

    public ExceptionDto(int code, String status, String message) {
        super();
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
