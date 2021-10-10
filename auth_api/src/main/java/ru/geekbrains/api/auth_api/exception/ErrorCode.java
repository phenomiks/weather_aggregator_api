package ru.geekbrains.api.auth_api.exception;

public enum ErrorCode {
    JSON_ERROR(ErrorCode.REPLACE),
    JSON_VALIDATION_ERROR(ErrorCode.REPLACE),
    EMAIL_VALIDATION_ERROR("Email " + ErrorCode.REPLACE + " is not valid"),
    USER_NOT_FOUND("User with login " + ErrorCode.REPLACE + " not found"),
    USER_ALREADY_REGISTERED_ERROR("User with this login or email is already registered"),
    PASSWORD_NOT_CORRECT("Password for user with login " + ErrorCode.REPLACE + " not correct");

    private static final String REPLACE = "<REPLACE>";

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
