package ru.geekbrains.api.dispatcher.application.utils;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import ru.geekbrains.api.dispatcher.application.exception.DispatcherApiException;
import ru.geekbrains.api.dispatcher.application.exception.ErrorCodes;


public class ExceptionHandlerForPostRequester {
    public static void timeOutException() {
        ObjectNode body = JsonResponseGenerator
                .generateErrorResponseJson(ErrorCodes.CONNECTION_REFUSED,
                        "Connection time out");

        throw new DispatcherApiException("Connection time out", body);
    }

    public static ResponseEntity<?> checkResponseHttpStatus(HttpStatusCodeException e) {
        if (e.getRawStatusCode() == HttpStatus.CONFLICT.value()) {
            return ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
                    .body(e.getResponseBodyAsString());
        }
        ObjectNode body = JsonResponseGenerator
                .generateErrorResponseJson(ErrorCodes.INTERNAL_ERROR,
                        "An internal error occurred. Try later");

        throw new DispatcherApiException("An internal error occurred. Try later", body);
    }
}
