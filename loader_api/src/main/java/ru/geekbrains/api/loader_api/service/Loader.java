package ru.geekbrains.api.loader_api.service;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Optional;

interface Loader {
    Optional<ObjectNode> getCurrentWeatherByCityName(String cityName);
    Optional<ObjectNode> getDailyWeatherByCityName(String cityName);
}
