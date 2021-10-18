package ru.geekbrains.api.dispatcher.exception;

import com.fasterxml.jackson.core.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.geekbrains.api.dispatcher.model.response.ErrorResponse;
import ru.geekbrains.api.dispatcher.model.response.Response;

@ResponseBody
@ControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler(DispatcherApiException.class)
    public ResponseEntity<Response> handleDispatcherApiException(DispatcherApiException exception) {
        return generateErrorResponse(exception.getResponse());
    }

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<Response> handleJsonParseException(JsonParseException exception) {
        Response response = new ErrorResponse(ErrorCode.JSON_ERROR, exception.getMessage());

        return generateErrorResponse(response);
    }

    private ResponseEntity<Response> generateErrorResponse(Response body) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(body);
    }
}
