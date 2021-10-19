package ru.geekbrains.api.data_api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;
import ru.geekbrains.api.data_api.exception.DataApiException;
import ru.geekbrains.api.data_api.exception.ErrorCode;
import ru.geekbrains.api.data_api.model.request.DataParameters;

import java.util.ArrayList;

@Component
public class ValidateRequestUtils {
    public DataParameters validateGetWeatherParameters(ObjectNode parameters) {
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
            throw new DataApiException("Json format error ",
                    ErrorCode.JSON_VALIDATION_ERROR,
                    "Problem with format translation");
        }
        return new DataParameters(city,services);
    }

    private void checkJsonFieldsCount(ObjectNode parameters, int count) {
        if (parameters.size() != count) {
            throw new DataApiException("Validation input parameters size not equals " + count,
                    ErrorCode.JSON_VALIDATION_ERROR,
                    "Check the number of fields in the message");
        }
    }

    private JsonNode checkJsonField(ObjectNode parameters, String fieldName) {
        if (parameters.has(fieldName)) {
            return parameters.path(fieldName);
        }
        throw new DataApiException(ErrorCode.JSON_VALIDATION_ERROR, "Not found " + fieldName + " field");
    }

    private String isStringJsonField(JsonNode field) {
        if (field.isTextual()) {
            return field.textValue();
        }
        throw new DataApiException(ErrorCode.JSON_VALIDATION_ERROR, field + " value must be a string");
    }

    private static void isArrayJsonField(JsonNode field) {
        if (!field.isArray()) {
            throw new DataApiException(ErrorCode.JSON_VALIDATION_ERROR, field + " value must be a array");
        }
    }

    private static void isArrayJsonFieldNotEmpty(JsonNode field) {
        if (field.isEmpty()) {
            throw new DataApiException(ErrorCode.JSON_VALIDATION_ERROR, field + " the value should not be empty");
        }
    }

    private static void isArrayStringJsonField(JsonNode field) {
        for (int i = 0; i < field.size(); i++) {
            if (!(field.get(i).getNodeType() == JsonNodeType.STRING)) {
                throw new DataApiException(ErrorCode.JSON_VALIDATION_ERROR, field + " the array should contain only strings");
            }
        }
    }

}
