package ru.geekbrains.api.auth_api.application.exception;

public enum ErrorCodes {
    JSON_ERROR(ErrorCodes.REPLACE),
    JSON_VALIDATION_ERROR(ErrorCodes.REPLACE),
    EMAIL_VALIDATION_ERROR("Email " + ErrorCodes.REPLACE + " is not valid"),
    USER_NOT_FOUND("User with login " + ErrorCodes.REPLACE + " not found");

    private static final String REPLACE = "<REPLACE>";

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
