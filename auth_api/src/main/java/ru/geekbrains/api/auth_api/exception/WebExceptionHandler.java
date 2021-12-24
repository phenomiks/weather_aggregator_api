package ru.geekbrains.api.auth_api.exception;

import com.fasterxml.jackson.core.JsonParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.geekbrains.api.auth_api.model.response.ErrorResponse;
import ru.geekbrains.api.auth_api.model.response.Response;

@ResponseBody
@ControllerAdvice
public class WebExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebExceptionHandler.class);

    @ExceptionHandler(AuthApiException.class)
    public ResponseEntity<Response> handleAuthApiException(AuthApiException exception) {
        return generateErrorResponse(exception.getResponse());
    }

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<Response> handleJsonParseException(JsonParseException exception) {
        Response response = new ErrorResponse(ErrorCode.JSON_ERROR, exception.getMessage());

        return generateErrorResponse(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        FieldError fieldError = exception.getBindingResult().getFieldError();
        String message = (fieldError == null)
                ? "Validation error. Contact technical support"
                : fieldError.getDefaultMessage();
        Response response = new ErrorResponse(ErrorCode.JSON_VALIDATION_ERROR, message);

        LOGGER.warn("{}. {}", ErrorCode.JSON_VALIDATION_ERROR, message);

        return generateErrorResponse(response);
    }

    private ResponseEntity<Response> generateErrorResponse(Response body) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(body);
    }
}
