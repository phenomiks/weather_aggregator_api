package ru.geekbrains.api.loader_api.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.geekbrains.api.loader_api.domain.WeatherRequest;
import ru.geekbrains.api.loader_api.domain.WeatherService;

import java.util.Map;

@Service
public interface LoaderService {
    ResponseEntity<?> getResponse(ObjectNode objectNode);

    Map<WeatherService, ObjectNode> getResponseFromWeatherServices(WeatherRequest weatherRequest);
}
