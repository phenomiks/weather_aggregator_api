package ru.geekbrains.api.loader_api.exception;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class LoaderApiException extends RuntimeException {
    private final ObjectNode node;

    public LoaderApiException(String message, ObjectNode node) {
        super(message);
        this.node = node;
    }

    public ObjectNode getNode() {
        return node;
    }
}
