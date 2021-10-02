package ru.geekbrains.api.auth_api.application.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import ru.geekbrains.api.auth_api.application.exception.AuthApiException;
import ru.geekbrains.api.auth_api.application.exception.ErrorCodes;
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

        return new UserRegParams(login, email, password);
    }

    private void checkJsonFieldsCount(ObjectNode parameters, int count) {
        if (parameters.size() != count) {
            throw new AuthApiException("Validation input parameters size not equals " + count,
                    ErrorCodes.JSON_VALIDATION_ERROR,
                    "Check the number of fields in the message");
        }
    }

    private JsonNode checkJsonField(ObjectNode parameters, String fieldName) {
        if (parameters.has(fieldName)) {
            return parameters.path(fieldName);
        }

        throw new AuthApiException(ErrorCodes.JSON_VALIDATION_ERROR, "Not found " + fieldName + " field");
    }

    private String isStringJsonField(JsonNode field) {
        if (field.isTextual()) {
            return field.textValue();
        }

        throw new AuthApiException(ErrorCodes.JSON_VALIDATION_ERROR, field + " value must be a string");
    }

    private void isValidEmail(String email) {
        boolean isValid = EmailValidator.getInstance().isValid(email);

        if (!isValid) {
            throw new AuthApiException(ErrorCodes.EMAIL_VALIDATION_ERROR, email);
        }
    }
}
