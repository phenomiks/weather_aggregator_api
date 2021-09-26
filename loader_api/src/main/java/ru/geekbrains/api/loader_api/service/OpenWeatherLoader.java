package ru.geekbrains.api.loader_api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@PropertySource("classpath:application.properties")
public class OpenWeatherLoader implements Loader {

    private static final String OPEN_WEATHER_URL = "api.openweathermap.org/data/2.5/weather";
    private static final String API_KEY_QUERY_PARAM = "appid";
    private static final String CITY_NAME_QUERY_PARAM = "q";
    private static final String QUESTION = "?";
    private static final String AND = "&";
    private static final String EQUAL = "=";
    private static final String HTTPS = "https://";

    private Logger LOGGER = LoggerFactory.getLogger(OpenWeatherLoader.class);

    @Value("${api.key.open.weather}")
    private String API_KEY;

    private RestTemplate restTemplate;

    @Autowired
    public OpenWeatherLoader(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Override
    public Optional<String> getByCityName(String cityName) {
        Optional<String> answer = Optional.ofNullable(restTemplate.getForObject(HTTPS + OPEN_WEATHER_URL + QUESTION +
                CITY_NAME_QUERY_PARAM + EQUAL + cityName + AND +
                API_KEY_QUERY_PARAM + EQUAL + API_KEY, String.class));
        if (answer.isPresent()) {
            LOGGER.info(answer.get());
            return answer;
        }

        return Optional.empty();
    }
}
