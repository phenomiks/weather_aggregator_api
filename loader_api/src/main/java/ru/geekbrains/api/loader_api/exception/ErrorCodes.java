package ru.geekbrains.api.loader_api.exception;

public enum ErrorCodes {
    JSON_ERROR(ErrorCodes.REPLACE),
    JSON_VALIDATION_ERROR(ErrorCodes.REPLACE),
    INTERNAL_ERROR("An internal error occurred. Try later"),
    CITY_NOT_FOUND("The city was not found");

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
