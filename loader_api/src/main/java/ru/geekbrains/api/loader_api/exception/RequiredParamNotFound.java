package ru.geekbrains.api.loader_api.exception;

public class RequiredParamNotFound extends RuntimeException {
    public RequiredParamNotFound(String requiredParam) {
        super("Not found " + requiredParam);
    }
}
