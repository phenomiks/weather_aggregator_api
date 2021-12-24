package ru.geekbrains.api.loader_api.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class OpenWeatherServiceImplTest {

    @Autowired
    private OpenWeatherServiceImpl openWeatherService;

    @ParameterizedTest
    @ValueSource(strings = {"Moscow", "New York", "Berlin"})
    void getCurrentWeatherByCityName(String cityName) {
        Optional<ObjectNode> answer = openWeatherService.getCurrentWeatherByCityName(cityName);
        assertNotNull(answer);
        assert answer.isPresent();

    }

    @ParameterizedTest
    @ValueSource(strings = {"Moscow", "New York", "Berlin"})
    void getDailyWeatherByCityName(String cityName) {
        Optional<ObjectNode> answer = openWeatherService.getDailyWeatherByCityName(cityName);
        assertNotNull(answer);
        assert answer.isPresent();
    }
}