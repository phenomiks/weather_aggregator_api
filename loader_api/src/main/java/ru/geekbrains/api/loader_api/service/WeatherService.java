package ru.geekbrains.api.loader_api.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
interface WeatherService {
    Optional<ObjectNode> getCurrentWeatherByCityName(String cityName);
    Optional<ObjectNode> getDailyWeatherByCityName(String cityName);
}
