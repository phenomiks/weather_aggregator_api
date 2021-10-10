package ru.geekbrains.api.auth_api.exception;

import ru.geekbrains.api.auth_api.model.response.ErrorResponse;

public class AuthApiException extends RuntimeException {
    private final ErrorResponse response;

    public AuthApiException(ErrorCode errorCode, String replaceText) {
        super(errorCode + ". " + errorCode.replaceAndGetMessage(replaceText));
        this.response = new ErrorResponse(errorCode, replaceText);
    }

    public AuthApiException(String message, ErrorCode errorCode, String replaceText) {
        super(message);
        this.response = new ErrorResponse(errorCode, replaceText);
    }

    public AuthApiException(String message, ErrorCode errorCode) {
        super(message);
        this.response = new ErrorResponse(errorCode, "");
    }

    public ErrorResponse getResponse() {
        return response;
    }
}
