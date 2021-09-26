package ru.geekbrains.api.dispatcher.application.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;
import ru.geekbrains.api.dispatcher.application.exception.DispatcherApiException;
import ru.geekbrains.api.dispatcher.application.exception.ErrorCodes;


@Component
public class ValidateRequestUtils {
    public void validateUserRegistrationParameters(ObjectNode parameters) {
        JsonNode keyField = checkJsonField(parameters, "key");
        isStringJsonField(keyField);
    }

    private JsonNode checkJsonField(ObjectNode parameters, String fieldName) {
        if (parameters.has(fieldName)) {
            return parameters.path(fieldName);
        }

        ObjectNode body = JsonResponseGenerator
                .generateErrorResponseJson(ErrorCodes.JSON_VALIDATION_ERROR,
                        "Not found " + fieldName + " field");

        throw new DispatcherApiException("Not found " + fieldName + " field", body);
    }

    private String isStringJsonField(JsonNode field) {
        if (field.isTextual()) {
            return field.textValue();
        }

        ObjectNode body = JsonResponseGenerator
                .generateErrorResponseJson(ErrorCodes.JSON_VALIDATION_ERROR,
                        field + " value must be a string");

        throw new DispatcherApiException(field + " value must be a string", body);
    }
}
