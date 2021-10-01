package ru.geekbrains.api.loader_api.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ru.geekbrains.api.loader_api.exception.ErrorCodes;
import ru.geekbrains.api.loader_api.exception.LoaderApiException;

public class ValidateRequestUtils {

    public static void validateWeatherRequest(ObjectNode weatherRequest) {
        int parametersCount = 2;
        checkJsonFieldsCount(weatherRequest, parametersCount);
        isStringJsonField(checkJsonField(weatherRequest, "city"));
        isArrayJsonField(checkJsonField(weatherRequest, "services"));
    }

    private static void checkJsonFieldsCount(ObjectNode parameters, int count) {
        if (parameters.size() != count) {
            ObjectNode body = JsonResponseGenerator
                    .generateErrorResponseJson(ErrorCodes.JSON_VALIDATION_ERROR,
                            "Check the number of fields in the message");

            throw new LoaderApiException("Validation input parameters size not equals " + count, body);
        }
    }

    private static JsonNode checkJsonField(ObjectNode parameters, String fieldName) {
        if (parameters.has(fieldName)) {
            return parameters.path(fieldName);
        }

        ObjectNode body = JsonResponseGenerator
                .generateErrorResponseJson(ErrorCodes.JSON_VALIDATION_ERROR,
                        "Not found " + fieldName + " field");

        throw new LoaderApiException("Not found " + fieldName + " field", body);
    }

    private static void isStringJsonField(JsonNode field) {
        if (!field.isTextual()) {
            ObjectNode body = JsonResponseGenerator
                    .generateErrorResponseJson(ErrorCodes.JSON_VALIDATION_ERROR,
                            field + " value must be a string");

            throw new LoaderApiException(field + " value must be a string", body);
        }
    }

    private static void isArrayJsonField(JsonNode field) {
        if (!field.isArray()) {
            ObjectNode body = JsonResponseGenerator
                    .generateErrorResponseJson(ErrorCodes.JSON_VALIDATION_ERROR,
                            field + " value must be a array");

            throw new LoaderApiException(field + " value must be a array", body);
        }
    }
}
