package ru.geekbrains.api.data_api.application.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;
import ru.geekbrains.api.data_api.application.exception.DataApiException;
import ru.geekbrains.api.data_api.application.exception.ErrorCodes;
import ru.geekbrains.api.data_api.request.DataParameters;

import java.util.ArrayList;


@Component
public class ValidateRequestUtils {
    public DataParameters validateUserRegistrationParameters(ObjectNode parameters) {
        int parametersCount = 2;
        checkJsonFieldsCount(parameters, parametersCount);

        JsonNode cityField = checkJsonField(parameters, "city");
        JsonNode servicesField = checkJsonField(parameters, "services");

        String city  = isStringJsonField(cityField);
        isArrayJsonField(servicesField);
        isArrayJsonFieldNotEmpty(servicesField);
        isArrayStringJsonField(servicesField);

        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<String> services = null;
        try {
            services = objectMapper.readValue(servicesField.toString(), ArrayList.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new DataParameters(city,services);
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

    private static void isArrayJsonField(JsonNode field) {
        if (!field.isArray()) {
            ObjectNode body = JsonResponseGenerator
                    .generateErrorResponseJson(ErrorCodes.JSON_VALIDATION_ERROR,
                            field + " value must be a array");

            throw new DataApiException(field + " value must be a array", body);
        }
    }

    private static void isArrayJsonFieldNotEmpty(JsonNode field) {
        if (field.isEmpty()) {
            ObjectNode body = JsonResponseGenerator
                    .generateErrorResponseJson(ErrorCodes.JSON_VALIDATION_ERROR,
                            field + " the value should not be empty");

            throw new DataApiException(field + " the value should not be empty", body);
        }
    }

    private static void isArrayStringJsonField(JsonNode field) {
        for (int i = 0; i < field.size(); i++) {
            if (!(field.get(i).getNodeType() == JsonNodeType.STRING)) {
                ObjectNode body = JsonResponseGenerator
                        .generateErrorResponseJson(ErrorCodes.JSON_VALIDATION_ERROR,
                                field + " the array should contain only strings");

                throw new DataApiException(field + " the array should contain only strings", body);
            }
        }
    }

}
