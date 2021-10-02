package ru.geekbrains.api.auth_api.model.response;

import ru.geekbrains.api.auth_api.application.exception.ErrorCodes;

public class Error {
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
