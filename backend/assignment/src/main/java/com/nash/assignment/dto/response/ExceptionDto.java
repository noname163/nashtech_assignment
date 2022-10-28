package com.nash.assignment.dto.response;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ExceptionDto {

    private String code;
    private String status;
    private String mess;
    @JsonInclude(Include.NON_NULL)
    private Map<String, String> errors;

    public ExceptionDto(String code, String status, Map<String, String> errors) {
        super();
        this.code = code;
        this.status = status;
        this.errors = errors;
    }

    public ExceptionDto(String code, String status, String mess) {
        super();
        this.code = code;
        this.status = status;
        this.mess = mess;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

}
