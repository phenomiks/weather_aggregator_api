package ru.geekbrains.api.dispatcher.exception;

public enum ErrorCode {
    JSON_ERROR(ErrorCode.REPLACE),
    JSON_VALIDATION_ERROR(ErrorCode.REPLACE),
    INTERNAL_ERROR("An internal error occurred. Try later"),
    CONNECTION_REFUSED("Connection time out"),
    KEY_EXPIRED("Your key has expired. You need to get a new key"),
    KEY_NOT_VALID("Your key is not valid");


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
