package ru.geekbrains.api.data_api.application.exception;

public enum ErrorCode {
    JSON_ERROR(ErrorCode.REPLACE),
    JSON_VALIDATION_ERROR(ErrorCode.REPLACE),
    CITY_NOT_FOUND("City " + ErrorCode.REPLACE + " not found"),
    INTERNAL_ERROR("An internal error occurred. Try later"),
    CONNECTION_REFUSED(ErrorCode.REPLACE);

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
