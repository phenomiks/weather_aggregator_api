package ru.geekbrains.api.loader_api.service;


import ru.geekbrains.api.loader_api.domain.City;

public interface GeoLoaderService {
    City getCity(String cityName);

    void putInCacheCity(City city);

    City findInCacheCity(String cityName);
}
