package ru.geekbrains.api.dispatcher.exception;

import ru.geekbrains.api.dispatcher.model.response.ErrorResponse;

public class DispatcherApiException extends RuntimeException {

    private final ErrorResponse response;

    public DispatcherApiException(ErrorCode errorCode, String replaceText) {
        super(errorCode + ". " + errorCode.replaceAndGetMessage(replaceText));
        this.response = new ErrorResponse(errorCode, replaceText);
    }

    public DispatcherApiException(String message, ErrorCode errorCode, String replaceText) {
        super(message);
        this.response = new ErrorResponse(errorCode, replaceText);
    }

    public DispatcherApiException(String message, ErrorCode errorCode) {
        super(message);
        this.response = new ErrorResponse(errorCode, "");
    }

    public ErrorResponse getResponse() {
        return response;
    }
}
