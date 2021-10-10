package ru.geekbrains.api.loader_api.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.geekbrains.api.loader_api.domain.WeatherRequest;
import ru.geekbrains.api.loader_api.domain.WeatherService;
import ru.geekbrains.api.loader_api.exception.ErrorCodes;
import ru.geekbrains.api.loader_api.exception.LoaderApiException;
import ru.geekbrains.api.loader_api.utils.JsonResponseGenerator;
import ru.geekbrains.api.loader_api.utils.ValidateRequestUtils;

import java.util.Optional;

@Service
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
    public Optional<ObjectNode> getByCityName(String cityName) {
        Optional<ObjectNode> answer = Optional.ofNullable(restTemplate.getForObject(HTTPS + OPEN_WEATHER_URL + QUESTION +
                CITY_NAME_QUERY_PARAM + EQUAL + cityName + AND +
                API_KEY_QUERY_PARAM + EQUAL + API_KEY, ObjectNode.class));
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
            Optional<ObjectNode> result = getByCityName(weatherRequest.getCity());
            response = result
                    .map(jsonNodes -> ResponseEntity.ok(JsonResponseGenerator.generateReportResponseJson(jsonNodes)))
                .orElseGet(() -> {
                    ObjectNode body = JsonResponseGenerator.generateErrorResponseJson(ErrorCodes.INTERNAL_ERROR, "");

                    throw new LoaderApiException("Error from the OpenWeather service", body);
                });
        }

        return response; // TODO add second weather service
    }
}
