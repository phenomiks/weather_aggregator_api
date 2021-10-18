package ru.geekbrains.api.dispatcher.utils;

import ru.geekbrains.api.dispatcher.exception.DispatcherApiException;
import ru.geekbrains.api.dispatcher.exception.ErrorCode;

public class ExceptionHandlerForToken {
    public static void keyExpiredException() {

        throw new DispatcherApiException(ErrorCode.KEY_EXPIRED,
                "Your key has expired. You need to get a new key");
    }

    public static void keyNotValidException() {

        throw new DispatcherApiException(ErrorCode.KEY_NOT_VALID,
                "Your key is not valid");
    }
}
