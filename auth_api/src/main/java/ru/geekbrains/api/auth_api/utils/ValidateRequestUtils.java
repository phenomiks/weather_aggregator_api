package ru.geekbrains.api.auth_api.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import ru.geekbrains.api.auth_api.exception.AuthApiException;
import ru.geekbrains.api.auth_api.exception.ErrorCode;
import ru.geekbrains.api.auth_api.model.request.UserParams;
import ru.geekbrains.api.auth_api.model.request.UserRegParams;

@Component
public class ValidateRequestUtils {
    public UserRegParams validateUserRegistrationParameters(ObjectNode parameters) {
        int parametersCount = 3;
        checkJsonFieldsCount(parameters, parametersCount);

        JsonNode loginField = checkJsonField(parameters, "login"); // TODO remove business exceptions
        JsonNode emailField = checkJsonField(parameters, "email");
        JsonNode passwordField = checkJsonField(parameters, "password");

        String login = isStringJsonField(loginField);
        String email = isStringJsonField(emailField);
        isValidEmail(email);
        char[] password = isStringJsonField(passwordField).toCharArray();

        return new UserRegParams(login, password, email);
    }

    public UserParams validateUserParameters(ObjectNode parameters) {
        int parametersCount = 2;
        checkJsonFieldsCount(parameters, parametersCount);

        JsonNode loginField = checkJsonField(parameters, "login");
        JsonNode passwordField = checkJsonField(parameters, "password");

        String login = isStringJsonField(loginField);
        char[] password = isStringJsonField(passwordField).toCharArray();

        return new UserParams(login, password);
    }

    private void checkJsonFieldsCount(ObjectNode parameters, int count) {
        if (parameters.size() != count) {
            throw new AuthApiException("Validation input parameters size not equals " + count,
                    ErrorCode.JSON_VALIDATION_ERROR,
                    "Check the number of fields in the message");
        }
    }

    private JsonNode checkJsonField(ObjectNode parameters, String fieldName) {
        if (parameters.has(fieldName)) {
            return parameters.path(fieldName);
        }

        throw new AuthApiException(ErrorCode.JSON_VALIDATION_ERROR, "Not found " + fieldName + " field");
    }

    private String isStringJsonField(JsonNode field) {
        if (field.isTextual()) {
            return field.textValue();
        }

        throw new AuthApiException(ErrorCode.JSON_VALIDATION_ERROR, field + " value must be a string");
    }

    private void isValidEmail(String email) {
        boolean isValid = EmailValidator.getInstance().isValid(email);

        if (!isValid) {
            throw new AuthApiException(ErrorCode.EMAIL_VALIDATION_ERROR, email);
        }
    }
}
