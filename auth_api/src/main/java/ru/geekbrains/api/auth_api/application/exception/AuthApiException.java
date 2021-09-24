package ru.geekbrains.api.auth_api.application.exception;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class AuthApiException extends RuntimeException {
    private final ObjectNode node;

    public AuthApiException(String message, ObjectNode node) {
        super(message);
        this.node = node;
    }

    public ObjectNode getNode() {
        return node;
    }
}
