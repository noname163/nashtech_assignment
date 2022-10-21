package com.nash.assignment.dto.response;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ExceptionResponseDto {
    private String code;
    private String status;
    @JsonInclude(Include.NON_NULL)
    private Map<String,String> errors;
    public ExceptionResponseDto(String code, String status, Map<String, String> errors) {
        super();
        this.code = code;
        this.status = status;
        this.errors = errors;
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

    


    
}
