package ru.geekbrains.api.auth_api.exception;

import com.fasterxml.jackson.core.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.geekbrains.api.auth_api.model.response.ErrorResponse;
import ru.geekbrains.api.auth_api.model.response.Response;

@ControllerAdvice
public class WebExceptionHandler {
    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<Response> handleAuthApiException(AuthApiException exception) {
        return generateErrorResponse(exception.getResponse());
    }

    @ExceptionHandler
    @ResponseBody
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
