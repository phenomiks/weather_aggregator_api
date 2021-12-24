package ru.geekbrains.api.dispatcher.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;
import ru.geekbrains.api.dispatcher.exception.DispatcherApiException;
import ru.geekbrains.api.dispatcher.exception.ErrorCode;


@Component
public class ValidateRequestUtils {
    public String validateUserRegistrationParameters(ObjectNode parameters) {
        JsonNode keyField = checkJsonField(parameters, "key");
        return isStringJsonField(keyField);
    }

    private JsonNode checkJsonField(ObjectNode parameters, String fieldName) {
        if (parameters.has(fieldName)) {
            return parameters.path(fieldName);
        }

        throw new DispatcherApiException(ErrorCode.JSON_VALIDATION_ERROR,
                "Not found " + fieldName + " field");
    }

    private String isStringJsonField(JsonNode field) {
        if (field.isTextual()) {
            return field.textValue();
        }

        throw new DispatcherApiException(ErrorCode.JSON_VALIDATION_ERROR,
                field + " value must be a string");
    }
}
