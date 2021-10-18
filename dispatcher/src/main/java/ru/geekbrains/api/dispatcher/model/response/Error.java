package ru.geekbrains.api.dispatcher.model.response;

import ru.geekbrains.api.dispatcher.exception.ErrorCode;

import java.io.Serializable;

public class Error implements Serializable {
    private static final long serialVersionUID = 2612116727050041763L;

    private ErrorCode errorCode;
    private String message;

    public Error(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
