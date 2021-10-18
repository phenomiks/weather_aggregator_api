package ru.geekbrains.api.dispatcher.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import ru.geekbrains.api.dispatcher.exception.DispatcherApiException;
import ru.geekbrains.api.dispatcher.exception.ErrorCode;


public class ExceptionHandlerForPostRequester {
    public static void timeOutException() {

        throw new DispatcherApiException(ErrorCode.CONNECTION_REFUSED,
                "Connection time out");
    }

    public static ResponseEntity<?> checkResponseHttpStatus(HttpStatusCodeException e) {
        if (e.getRawStatusCode() == HttpStatus.CONFLICT.value()) {
            return ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
                    .body(e.getResponseBodyAsString());
        }

        throw new DispatcherApiException(ErrorCode.INTERNAL_ERROR,
                "An internal error occurred. Try later");
    }
}
