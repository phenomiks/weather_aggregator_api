package ru.geekbrains.api.auth_api.model.response;

import ru.geekbrains.api.auth_api.application.exception.ErrorCodes;

public class ErrorResponse extends Response {
    public static final String ERROR_STATUS = "error";
    private Error error;

    public ErrorResponse(ErrorCodes errorCode, String replaceText) {
        super(ERROR_STATUS);
        this.error = new Error(errorCode, errorCode.replaceAndGetMessage(replaceText));
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
