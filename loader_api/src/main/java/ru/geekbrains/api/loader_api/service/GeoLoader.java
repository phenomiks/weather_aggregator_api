package ru.geekbrains.api.loader_api.service;


import ru.geekbrains.api.loader_api.domain.City;

public interface GeoLoader {
    City getCity(String cityName);
}
