package ru.geekbrains.api.dispatcher.exception;

public enum ErrorCode {
    JSON_ERROR(ErrorCode.REPLACE),
    JSON_VALIDATION_ERROR(ErrorCode.REPLACE),
    INTERNAL_ERROR(ErrorCode.REPLACE),
    CONNECTION_REFUSED(ErrorCode.REPLACE),
    KEY_EXPIRED(ErrorCode.REPLACE),
    KEY_NOT_VALID(ErrorCode.REPLACE);


    private final static String REPLACE = "<REPLACE>";

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String replaceAndGetMessage(String text) {
        return getMessage().replace(ErrorCode.REPLACE, text);
    }
}
