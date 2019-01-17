package com.pactera.config.exception;

import org.springframework.http.HttpStatus;

import com.pactera.config.exception.status.ErrorStatus;

public class DataStoreException extends RuntimeException {
    private static final long serialVersionUID = -2319097667755215294L;

    private Integer status;
    private String message;
    private HttpStatus httpStatus;

    public DataStoreException() {
        super();
    }

    public DataStoreException(String message) {
        this.message = message;
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public DataStoreException(ErrorStatus errorStatus) {
        this.status = errorStatus.status();
        this.message = errorStatus.message();
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public DataStoreException(Integer status, String message) {
        this.status = status;
        this.message = message;
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public DataStoreException(HttpStatus httpStatus, String message) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public DataStoreException(HttpStatus httpStatus, ErrorStatus errorStatus) {
        this.status = errorStatus.status();
        this.message = errorStatus.message();
        this.httpStatus = httpStatus;
    }

    public DataStoreException(HttpStatus httpStatus, Integer status, String message) {
        this.status = status;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

}
