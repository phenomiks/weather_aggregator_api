package ru.geekbrains.api.dispatcher.application.exception;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class DispatcherApiException extends RuntimeException {
    private final ObjectNode node;

    public DispatcherApiException(String message, ObjectNode node) {
        super(message);
        this.node = node;
    }

    public ObjectNode getNode() {
        return node;
    }
}
