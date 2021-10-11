package ru.geekbrains.api.data_api.application.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import ru.geekbrains.api.data_api.application.exception.DataApiException;
import ru.geekbrains.api.data_api.application.exception.ErrorCode;



public class ExceptionHandlerForRestService {

    public static ResponseEntity<?> checkResponseHttpStatus(HttpStatusCodeException e) {
        if (e.getRawStatusCode() == HttpStatus.CONFLICT.value()) {
            return ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
                    .body(e.getResponseBodyAsString());
        }
        throw new DataApiException("An internal error occurred. Try later", ErrorCode.INTERNAL_ERROR, "");
    }
}
