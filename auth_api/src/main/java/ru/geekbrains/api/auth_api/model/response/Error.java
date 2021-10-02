package ru.geekbrains.api.auth_api.model.response;

import ru.geekbrains.api.auth_api.application.exception.ErrorCodes;

import java.io.Serializable;

public class Error implements Serializable {
    private static final long serialVersionUID = 2612116727050041763L;

    private ErrorCodes errorCode;
    private String message;

    public Error(ErrorCodes errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public ErrorCodes getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCodes errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
