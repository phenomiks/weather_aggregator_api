package ru.geekbrains.api.dispatcher.utils;

import com.fasterxml.jackson.databind.node.ObjectNode;
import ru.geekbrains.api.dispatcher.exception.DispatcherApiException;
import ru.geekbrains.api.dispatcher.exception.ErrorCode;

public class ExceptionHandlerForToken {
    public static void keyExpiredException() {
        ObjectNode body = JsonResponseGenerator
                .generateErrorResponseJson(ErrorCode.KEY_EXPIRED,
                        "Your key has expired. You need to get a new key");

        throw new DispatcherApiException("Your key has expired. You need to get a new key", body);
    }

    public static void keyNotValidException() {
        ObjectNode body = JsonResponseGenerator
                .generateErrorResponseJson(ErrorCode.KEY_NOT_VALID,
                        "Your key is not valid");

        throw new DispatcherApiException("Your key is not valid", body);
    }
}
