package ru.geekbrains.api.loader_api.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ru.geekbrains.api.loader_api.domain.City;
import ru.geekbrains.api.loader_api.utils.ValidatorResponseUtils;

import java.util.Optional;

import static ru.geekbrains.api.loader_api.utils.HttpRequestConstants.HTTPS;
import static ru.geekbrains.api.loader_api.utils.YandexWeatherRequestConstants.*;

@Service
@PropertySource("classpath:private.properties")
public class YandexWeatherServiceImpl implements WeatherService {

    @Value("${api.key.yandex.weather}")
    private String API_KEY;
    private final Logger LOGGER = LoggerFactory.getLogger(YandexWeatherServiceImpl.class);
    private final RestTemplate restTemplate;
    private final GeoLoaderService geoLoaderService;

    @Autowired
    public YandexWeatherServiceImpl(GeoLoaderService geoLoaderService, RestTemplateBuilder builder) {
        this.geoLoaderService = geoLoaderService;
        this.restTemplate = builder.build();
    }

    @Override
    public Optional<ObjectNode> getCurrentWeatherByCityName(String cityName) {
        //Yandex test gives only daily weather
        return getDailyWeatherByCityName(cityName);
    }

    @Override
    public Optional<ObjectNode> getDailyWeatherByCityName(String cityName) {
        City city = geoLoaderService.getCity(cityName);

        ValidatorResponseUtils.validateCity(city);

        HttpHeaders headers = new HttpHeaders();
        headers.set(YANDEX_API_KEY, API_KEY);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        UriComponents urlBuilder = UriComponentsBuilder.newInstance()
                .scheme(HTTPS).host(YANDEX_WEATHER_FORECAST_URL)
                .queryParam(LANG, RU)
                .queryParam(LATITUDE, city.getLat().toString())
                .queryParam(LONGITUDE, city.getLon().toString())
                .build();

        ResponseEntity<ObjectNode> answer = restTemplate.exchange(urlBuilder.toUriString(), HttpMethod.GET, entity, ObjectNode.class);

        if (answer.getBody() != null) {
            LOGGER.info("Response: " + answer);
        }

        return Optional.ofNullable(answer.getBody());
    }
}
