package ru.geekbrains.api.data_api.application.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;
import ru.geekbrains.api.data_api.application.exception.DataApiException;
import ru.geekbrains.api.data_api.application.exception.ErrorCodes;
import ru.geekbrains.api.data_api.request.DataParameters;


@Component
public class ValidateRequestUtils {
    public DataParameters validateUserRegistrationParameters(ObjectNode parameters) {
        int parametersCount = 2;
        checkJsonFieldsCount(parameters, parametersCount);

        JsonNode cityField = checkJsonField(parameters, "city");
        JsonNode servicesField = checkJsonField(parameters, "services");

        String city = isStringJsonField(cityField);
        String services = isStringJsonField(servicesField);

        return new DataParameters(city, services);
    }

    private void checkJsonFieldsCount(ObjectNode parameters, int count) {
        if (parameters.size() != count) {
            ObjectNode body = JsonResponseGenerator
                    .generateErrorResponseJson(ErrorCodes.JSON_VALIDATION_ERROR,
                            "Check the number of fields in the message");

            throw new DataApiException("Validation input parameters size not equals " + count, body);
        }
    }

    private JsonNode checkJsonField(ObjectNode parameters, String fieldName) {
        if (parameters.has(fieldName)) {
            return parameters.path(fieldName);
        }

        ObjectNode body = JsonResponseGenerator
                .generateErrorResponseJson(ErrorCodes.JSON_VALIDATION_ERROR,
                        "Not found " + fieldName + " field");

        throw new DataApiException("Not found " + fieldName + " field", body);
    }

    private String isStringJsonField(JsonNode field) {
        if (field.isTextual()) {
            return field.textValue();
        }

        ObjectNode body = JsonResponseGenerator
                .generateErrorResponseJson(ErrorCodes.JSON_VALIDATION_ERROR,
                        field + " value must be a string");

        throw new DataApiException(field + " value must be a string", body);
    }
}
