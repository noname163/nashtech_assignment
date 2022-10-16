package com.nash.assignment.modal;

public class ResponseObject {

    private String status;
    private String message;
    private String statusCode;
    private Object data;

    public ResponseObject() {
    }

    public ResponseObject(String statusCode, String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.statusCode = statusCode;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getstatusCode() {
        return statusCode;
    }

    public void setstatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
}
