package ru.geekbrains.api.loader_api.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ru.geekbrains.api.loader_api.domain.City;
import ru.geekbrains.api.loader_api.utils.ValidatorResponseUtils;

import java.util.Optional;

import static ru.geekbrains.api.loader_api.utils.HttpRequestConstants.HTTP;
import static ru.geekbrains.api.loader_api.utils.HttpRequestConstants.HTTPS;
import static ru.geekbrains.api.loader_api.utils.OpenWeatherRequestConstants.*;

@Service
@PropertySource("classpath:private.properties")
public class OpenWeatherServiceImpl implements WeatherService {

    @Value("${api.key.open.weather}")
    private String API_KEY;

    private Logger LOGGER = LoggerFactory.getLogger(OpenWeatherServiceImpl.class);

    private RestTemplate restTemplate;
    private GeoLoaderService geoLoader;

    @Autowired
    public OpenWeatherServiceImpl(RestTemplateBuilder builder, GeoLoaderService geoLoader) {
        this.restTemplate = builder.build();
        this.geoLoader = geoLoader;
    }

    @Override
    public Optional<ObjectNode> getCurrentWeatherByCityName(String cityName) {
        UriComponents urlBuilder = UriComponentsBuilder.newInstance()
                .scheme(HTTP).host(OPEN_WEATHER_URL)
                .queryParam(API_KEY_QUERY_PARAM, API_KEY)
                .queryParam(CITY_NAME_QUERY_PARAM, cityName)
                .build();

        Optional<ObjectNode> answer = Optional.ofNullable(restTemplate.getForObject(urlBuilder.toUriString(), ObjectNode.class));

        if (answer.isPresent()) {
            LOGGER.info(String.valueOf(answer.get()));
            return answer;
        }

        return Optional.empty();
    }

    @Override
    public Optional<ObjectNode> getDailyWeatherByCityName(String cityName) {
        City city = geoLoader.getCity(cityName);
        ValidatorResponseUtils.validateCity(city);

        UriComponents urlBuilder = UriComponentsBuilder.newInstance()
                .scheme(HTTPS).host(ONE_CALL_URL)
                .queryParam(API_KEY_QUERY_PARAM, API_KEY)
                .queryParam(LATITUDE, city.getLat())
                .queryParam(LONGITUDE, city.getLon())
                .queryParam(EXCLUDE, MINUTELY, ALERTS)
                .queryParam(UNITS_QUERY_PARAM, METRIC_QUERY_PARAM)
                .build();

        Optional<ObjectNode> answer = Optional.ofNullable(restTemplate.getForObject(urlBuilder.toUriString(), ObjectNode.class));

        if (answer.isPresent()) {
            LOGGER.info("Response: " + answer.get());
            return answer;
        }

        return Optional.empty();
    }


}
