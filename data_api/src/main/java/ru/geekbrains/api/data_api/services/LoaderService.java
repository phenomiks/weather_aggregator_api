package ru.geekbrains.api.data_api.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.geekbrains.api.data_api.exception.DataApiException;
import ru.geekbrains.api.data_api.exception.ErrorCode;
import ru.geekbrains.api.data_api.utils.JsonResponseGenerator;
import ru.geekbrains.api.data_api.model.WeatherService;
import ru.geekbrains.api.data_api.model.response.ReportResponse;
import ru.geekbrains.api.data_api.model.request.DataParameters;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class LoaderService {
    @Value("${loader.getWeather}")
    private String url;
    private final RestService restService;

    @Autowired
    public LoaderService(RestService restService) {
        this.restService = restService;
    }

    public ResponseEntity<ObjectNode> loaderWeather(DataParameters dataParameters){
        boolean detailed = dataParameters.needDetailed();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.valueToTree(dataParameters);
        ResponseEntity<ObjectNode> responseEntity = restService.doPost(jsonNode, url);
        if (responseEntity == null){
            throw new DataApiException("Connection time out ",
                    ErrorCode.CONNECTION_REFUSED,
                    "Connection time out");
        }
        if (detailed) {
           return responseEntity;
        }

        return checkResponseStatus(responseEntity);
    }

    private ResponseEntity<ObjectNode> checkResponseStatus(ResponseEntity<ObjectNode> response) {
        if (response.getStatusCode() != HttpStatus.OK) {
            return response;
        }

        ObjectNode responseBody = response.getBody();
        if (responseBody != null && responseBody.has("status")) {
            String status = responseBody.path("status").textValue();
            if (!status.equals(ReportResponse.REPORT_STATUS)) {
                return response;
            }

            return prepareShortWeatherResponse(responseBody);
        }

        return response;
    }

    private ResponseEntity<ObjectNode> prepareShortWeatherResponse(ObjectNode responseBody) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode body = mapper.createObjectNode();

        JsonNode responseReport = responseBody.path(ReportResponse.REPORT_STATUS);
        if (responseReport.has(WeatherService.OPEN_WEATHER.getName())) {
            boolean isNullOpenWeather = responseReport.path(WeatherService.OPEN_WEATHER.getName()).isNull();
            if (!isNullOpenWeather) {
                JsonNode openWeather = responseReport.path(WeatherService.OPEN_WEATHER.getName());
                ObjectNode node = mapper.createObjectNode();
                JsonNode current = openWeather.path("current");
                JsonNode daily = openWeather.path("daily");
                node.set("current", current);
                node.set("daily", daily);

                body.set(WeatherService.OPEN_WEATHER.getName(), node);
            }
        }

        if (responseReport.has(WeatherService.YANDEX_WEATHER.getName())) {
            boolean isNullYandexWeather = responseReport.path(WeatherService.YANDEX_WEATHER.getName()).isNull();
            if (!isNullYandexWeather) {
                JsonNode yandexWeather = responseReport.path(WeatherService.YANDEX_WEATHER.getName());
                ObjectNode node = mapper.createObjectNode();
                JsonNode fact = yandexWeather.path("fact");
                JsonNode forecasts = yandexWeather.path("forecasts");
                node.set("fact", fact);
                node.set("forecasts", forecasts);
                while (node.findParent("hours") != null && node.findParent("night_short") != null && node.findParent("day_short") != null){
                    node.findParent("hours").remove("hours");
                    node.findParent("day_short").remove("day_short");
                    node.findParent("night_short").remove("night_short");
                }

                body.set(WeatherService.YANDEX_WEATHER.getName(), node);
            }
        }

        if (body.isEmpty()) {
            throw new DataApiException(ErrorCode.INTERNAL_ERROR, "");
        }

        ObjectNode report = JsonResponseGenerator.generateReportResponseJson(body);
        return ResponseEntity.ok(report);
    }

}
