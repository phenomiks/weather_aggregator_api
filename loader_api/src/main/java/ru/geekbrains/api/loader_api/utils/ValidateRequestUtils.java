package ru.geekbrains.api.loader_api.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ru.geekbrains.api.loader_api.domain.WeatherRequest;
import ru.geekbrains.api.loader_api.domain.WeatherService;
import ru.geekbrains.api.loader_api.exception.ErrorCodes;
import ru.geekbrains.api.loader_api.exception.LoaderApiException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidateRequestUtils {

    public static WeatherRequest validateWeatherRequest(ObjectNode weatherRequest) {
        int parametersCount = 2;
        checkJsonFieldsCount(weatherRequest, parametersCount);
        String city = isStringJsonField(checkJsonField(weatherRequest, "city"));
        Set<String> services = isArrayJsonField(checkJsonField(weatherRequest, "services"));

        Set<WeatherService> weatherServices = checkWeatherService(services);

        return new WeatherRequest(city, weatherServices);
    }

    private static Set<WeatherService> checkWeatherService(Set<String> services) {
        Set<String> weatherServiceNames = Arrays.stream(WeatherService.values())
                .map(WeatherService::getName)
                .collect(Collectors.toSet());

        WeatherService[] weatherServices = WeatherService.values();
        Set<WeatherService> result = new HashSet<>();
        for (String serviceName : services) {
            if (weatherServiceNames.contains(serviceName)) {
                for (WeatherService ws : weatherServices) {
                    if (ws.getName().equalsIgnoreCase(serviceName)) {
                        result.add(ws);
                        break;
                    }
                }
            } else {
                ObjectNode body = JsonResponseGenerator
                        .generateErrorResponseJson(ErrorCodes.JSON_VALIDATION_ERROR,
                                "Service " + serviceName + " not supported");

                throw new LoaderApiException("Service " + serviceName + " not supported", body);
            }
        }

        return result;
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

    private static String isStringJsonField(JsonNode field) {
        if (!field.isTextual()) {
            ObjectNode body = JsonResponseGenerator
                    .generateErrorResponseJson(ErrorCodes.JSON_VALIDATION_ERROR,
                            field + " value must be a string");

            throw new LoaderApiException(field + " value must be a string", body);
        }

        return field.textValue();
    }

    private static Set<String> isArrayJsonField(JsonNode field) {
        if (!field.isArray()) {
            ObjectNode body = JsonResponseGenerator
                    .generateErrorResponseJson(ErrorCodes.JSON_VALIDATION_ERROR,
                            field + " value must be a array");

            throw new LoaderApiException(field + " value must be a array", body);
        }

        Set<String> services = new HashSet<>();
        for (JsonNode node : field) {
            String service = isStringJsonField(node).toLowerCase();
            services.add(service);
        }

        return services;
    }
}
