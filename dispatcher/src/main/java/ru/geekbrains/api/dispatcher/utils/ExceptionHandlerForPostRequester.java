package ru.geekbrains.api.dispatcher.utils;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import ru.geekbrains.api.dispatcher.exception.DispatcherApiException;
import ru.geekbrains.api.dispatcher.exception.ErrorCode;


public class ExceptionHandlerForPostRequester {
    public static void timeOutException() {
        ObjectNode body = JsonResponseGenerator
                .generateErrorResponseJson(ErrorCode.CONNECTION_REFUSED,
                        "Connection time out");

        throw new DispatcherApiException("Connection time out", body);
    }

    public static ResponseEntity<?> checkResponseHttpStatus(HttpStatusCodeException e) {
        if (e.getRawStatusCode() == HttpStatus.CONFLICT.value()) {
            return ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
                    .body(e.getResponseBodyAsString());
        }
        ObjectNode body = JsonResponseGenerator
                .generateErrorResponseJson(ErrorCode.INTERNAL_ERROR,
                        "An internal error occurred. Try later");

        throw new DispatcherApiException("An internal error occurred. Try later", body);
    }
}
