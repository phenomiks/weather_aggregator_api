package ru.geekbrains.api.dispatcher.exception;

public enum ErrorCodes {
    JSON_ERROR(ErrorCodes.REPLACE),
    JSON_VALIDATION_ERROR(ErrorCodes.REPLACE),
    INTERNAL_ERROR(ErrorCodes.REPLACE),
    CONNECTION_REFUSED(ErrorCodes.REPLACE),
    KEY_EXPIRED(ErrorCodes.REPLACE),
    KEY_NOT_VALID(ErrorCodes.REPLACE);


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
