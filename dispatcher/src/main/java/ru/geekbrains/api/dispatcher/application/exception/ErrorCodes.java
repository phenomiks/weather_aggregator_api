package ru.geekbrains.api.dispatcher.application.exception;

public enum ErrorCodes {
    JSON_ERROR(ErrorCodes.REPLACE),
    JSON_VALIDATION_ERROR(ErrorCodes.REPLACE);


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
