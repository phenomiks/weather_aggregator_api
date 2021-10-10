package ru.geekbrains.api.loader_api.service;

import java.util.Optional;

public interface GeoLoader {
    Optional<Object> getCityCoordinate(String cityName);
}
