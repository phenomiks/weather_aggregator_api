package ru.geekbrains.api.loader_api.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.geekbrains.api.loader_api.domain.City;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
class GeocodingLoaderServiceImplTest {

    @Autowired
    private GeoLoaderService geoLoaderService;

    @ParameterizedTest
    @ValueSource(strings = {"Moscow", "New York", "Berlin"})
    void getCity(String cityName) {
        City city = geoLoaderService.getCity(cityName);
        assertNotNull(city);
        assertNotNull(city.getName());
        assertNotNull(city.getLat());
        assertNotNull(city.getLon());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Moscow", "New York", "Berlin"})
    void putAndFindInCacheCity(String cityName) {
        City city = new City(cityName, 0D, 0D);
        geoLoaderService.putInCacheCity(city);
        assertNotNull(geoLoaderService.findInCacheCity(cityName));
        assertEquals(city, geoLoaderService.findInCacheCity(cityName));
    }

}