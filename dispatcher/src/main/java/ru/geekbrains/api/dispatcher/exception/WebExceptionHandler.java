package ru.geekbrains.api.dispatcher.exception;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.geekbrains.api.dispatcher.utils.JsonResponseGenerator;

@ControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<?> handleDispatcherApiException(DispatcherApiException exception) {
        return generateErrorResponse(exception.getNode());
    }

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<?> handleJsonParseException(JsonParseException exception) {
        ObjectNode body = JsonResponseGenerator
                .generateErrorResponseJson(ErrorCode.JSON_ERROR,exception.getMessage());

        return generateErrorResponse(body);
    }

    private ResponseEntity<?> generateErrorResponse(ObjectNode body) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(body);
    }
}
