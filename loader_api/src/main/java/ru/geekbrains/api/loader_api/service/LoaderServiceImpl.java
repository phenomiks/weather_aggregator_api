package ru.geekbrains.api.loader_api.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.geekbrains.api.loader_api.domain.WeatherRequest;
import ru.geekbrains.api.loader_api.domain.WeatherService;
import ru.geekbrains.api.loader_api.utils.JsonResponseGenerator;
import ru.geekbrains.api.loader_api.utils.ValidateRequestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.geekbrains.api.loader_api.domain.WeatherService.OPEN_WEATHER;
import static ru.geekbrains.api.loader_api.domain.WeatherService.YANDEX_WEATHER;
import static ru.geekbrains.api.loader_api.exception.ErrorCodes.WEATHER_SERVICE_ERROR;

@Service
public class LoaderServiceImpl implements LoaderService {

    private final OpenWeatherServiceImpl openWeatherService;
    private final YandexWeatherServiceImpl yandexWeatherService;

    @Autowired
    public LoaderServiceImpl(OpenWeatherServiceImpl openWeatherService, YandexWeatherServiceImpl yandexWeatherService) {
        this.openWeatherService = openWeatherService;
        this.yandexWeatherService = yandexWeatherService;
    }

    @Override
    public ResponseEntity<?> getResponse(ObjectNode objectNode) {
        WeatherRequest weatherRequest = ValidateRequestUtils.validateWeatherRequest(objectNode);
        Map<WeatherService, ObjectNode> weatherServicesMap = getResponseFromWeatherServices(weatherRequest);

        if (hasNullResponse(weatherServicesMap)) {
            return new ResponseEntity<>(JsonResponseGenerator.generateErrorResponseJson(WEATHER_SERVICE_ERROR, "Weather not found"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(JsonResponseGenerator.generateReportResponseJson(weatherServicesMap), HttpStatus.OK);
    }

    public Map<WeatherService, ObjectNode> getResponseFromWeatherServices(WeatherRequest weatherRequest) {
        Map<WeatherService, ObjectNode> objectNodes = new HashMap<>();

        if (weatherRequest.getWeatherServices().contains(OPEN_WEATHER)) {
            Optional<ObjectNode> optionalOpenWeather = openWeatherService.getDailyWeatherByCityName(weatherRequest.getCity());
            optionalOpenWeather.ifPresent(objectNode -> objectNodes.put(OPEN_WEATHER, objectNode));
        } else {
            objectNodes.put(OPEN_WEATHER, null);
        }

        if (weatherRequest.getWeatherServices().contains(YANDEX_WEATHER)) {
            Optional<ObjectNode> optionalYandexWeather = yandexWeatherService.getDailyWeatherByCityName(weatherRequest.getCity());
            optionalYandexWeather.ifPresent(objectNode -> objectNodes.put(YANDEX_WEATHER, objectNode));
        } else {
            objectNodes.put(YANDEX_WEATHER, null);
        }

        return objectNodes;
    }

    private boolean hasNullResponse(Map<WeatherService, ObjectNode> weatherServicesMap) {
        AtomicInteger countNullResponse = new AtomicInteger();

        weatherServicesMap.forEach((service, node) -> {
            if (node == null) {
                countNullResponse.getAndIncrement();
            }
        });

        return weatherServicesMap.size() == countNullResponse.get();
    }
}
