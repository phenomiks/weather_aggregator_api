package ru.geekbrains.api.data_api.exception;

import ru.geekbrains.api.data_api.model.response.ErrorResponse;

public class DataApiException extends RuntimeException {
    private final ErrorResponse response;

    public DataApiException(ErrorCode errorCode, String replaceText) {
        super(errorCode + ". " + errorCode.replaceAndGetMessage(replaceText));
        this.response = new ErrorResponse(errorCode, replaceText);
    }

    public DataApiException(String message, ErrorCode errorCode, String replaceText) {
        super(message);
        this.response = new ErrorResponse(errorCode, replaceText);
    }

    public ErrorResponse getResponse() {
        return response;
    }
}
