package ru.geekbrains.api.data_api.application.exception;

public enum ErrorCodes {
    JSON_ERROR(ErrorCodes.REPLACE),
    JSON_VALIDATION_ERROR(ErrorCodes.REPLACE),
    CITY_NOT_FOUND("City " + ErrorCodes.REPLACE + " not found");

    private final static String REPLACE = "<REPLACE>";

    private final String message;

    ErrorCodes(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String replaceAndGetMessage(String text) {
        return getMessage().replace(ErrorCodes.REPLACE, text);
    }
}
