package ru.geekbrains.api.data_api.application.utils;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import ru.geekbrains.api.data_api.application.exception.DataApiException;
import ru.geekbrains.api.data_api.application.exception.ErrorCodes;



public class ExceptionHandlerForRestService {
    public static void timeOutException() {
        ObjectNode body = JsonResponseGenerator
                .generateErrorResponseJson(ErrorCodes.CONNECTION_REFUSED,
                        "Connection time out");

        throw new DataApiException("Connection time out", body);
    }

    public static ResponseEntity<?> checkResponseHttpStatus(HttpStatusCodeException e) {
        if (e.getRawStatusCode() == HttpStatus.CONFLICT.value()) {
            return ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
                    .body(e.getResponseBodyAsString());
        }
        ObjectNode body = JsonResponseGenerator
                .generateErrorResponseJson(ErrorCodes.INTERNAL_ERROR,
                        "An internal error occurred. Try later");

        throw new DataApiException("An internal error occurred. Try later", body);
    }
}
