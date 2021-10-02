package ru.geekbrains.api.auth_api.application.exception;

import ru.geekbrains.api.auth_api.model.response.ErrorResponse;

public class AuthApiException extends RuntimeException {
    private final ErrorResponse response;

    public AuthApiException(ErrorCodes errorCode, String replaceText) {
        super(errorCode + ". " + errorCode.replaceAndGetMessage(replaceText));
        this.response = new ErrorResponse(errorCode, replaceText);
    }

    public AuthApiException(String message, ErrorCodes errorCode, String replaceText) {
        super(message);
        this.response = new ErrorResponse(errorCode, replaceText);
    }

    public ErrorResponse getResponse() {
        return response;
    }
}
