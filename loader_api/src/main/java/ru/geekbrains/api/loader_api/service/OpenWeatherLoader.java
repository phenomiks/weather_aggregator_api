package ru.geekbrains.api.loader_api.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ru.geekbrains.api.loader_api.domain.City;
import ru.geekbrains.api.loader_api.domain.WeatherRequest;
import ru.geekbrains.api.loader_api.domain.WeatherService;
import ru.geekbrains.api.loader_api.exception.ErrorCodes;
import ru.geekbrains.api.loader_api.exception.LoaderApiException;
import ru.geekbrains.api.loader_api.utils.JsonResponseGenerator;
import ru.geekbrains.api.loader_api.utils.ValidateRequestUtils;

import java.util.Optional;

import static ru.geekbrains.api.loader_api.utils.HttpRequestConstants.HTTP;
import static ru.geekbrains.api.loader_api.utils.HttpRequestConstants.HTTPS;
import static ru.geekbrains.api.loader_api.utils.OpenWeatherRequestConstants.*;

@Service
@PropertySource("classpath:private.properties")
public class OpenWeatherLoader implements Loader {

    @Value("${api.key.open.weather}")
    private String API_KEY;

    private Logger LOGGER = LoggerFactory.getLogger(OpenWeatherLoader.class);

    private RestTemplate restTemplate;
    private GeoLoader geoLoader;

    @Autowired
    public OpenWeatherLoader(RestTemplateBuilder builder, GeoLoader geoLoader) {
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
        if (city.getLat() == null || city.getLon() == null) {
            throw new IllegalArgumentException("City should have latitude and longitude");
        }

        UriComponents urlBuilder = UriComponentsBuilder.newInstance()
                .scheme(HTTPS).host(ONE_CALL_URL)
                .queryParam(API_KEY_QUERY_PARAM, API_KEY)
                .queryParam(LATITUDE, city.getLat())
                .queryParam(LONGITUDE, city.getLon())
                .queryParam(UNITS_QUERY_PARAM, METRIC_QUERY_PARAM)
                .build();

        Optional<ObjectNode> answer = Optional.ofNullable(restTemplate.getForObject(urlBuilder.toUriString(), ObjectNode.class));

        if (answer.isPresent()) {
            LOGGER.info(String.valueOf(answer.get()));
            return answer;
        }

        return Optional.empty();
    }

    public ResponseEntity<?> getResponse(ObjectNode objectNode) {
        WeatherRequest weatherRequest = ValidateRequestUtils.validateWeatherRequest(objectNode);

        ResponseEntity<?> response = null;
        if (weatherRequest.getWeatherServices().contains(WeatherService.OPEN_WEATHER)) {
            Optional<ObjectNode> result = getDailyWeatherByCityName(weatherRequest.getCity());
            response = result
                    .map(jsonNodes -> ResponseEntity.ok(JsonResponseGenerator.generateReportResponseJson(jsonNodes, WeatherService.OPEN_WEATHER)))
                    .orElseGet(() -> {
                        ObjectNode body = JsonResponseGenerator.generateErrorResponseJson(ErrorCodes.INTERNAL_ERROR, "");

                        throw new LoaderApiException("Error from the OpenWeather service", body);
                    });
        }

        return response; // TODO add second weather service
    }
}
