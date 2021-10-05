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
import ru.geekbrains.api.loader_api.exception.ErrorCodes;
import ru.geekbrains.api.loader_api.utils.JsonResponseGenerator;

import java.util.Optional;

@Service
public class GeocodingLoader implements GeoLoader {
    private static final String Geocoding_URL = "api.openweathermap.org/geo/1.0/direct";
    private static final String API_KEY_QUERY_PARAM = "appid";
    private static final String CITY_NAME_QUERY_PARAM = "q";
    private static final String QUESTION = "?";
    private static final String AND = "&";
    private static final String EQUAL = "=";
    private static final String HTTP = "http://";

    private Logger LOGGER = LoggerFactory.getLogger(GeocodingLoader.class);

    @Value("${api.key.open.weather}")
    private String API_KEY;

    private RestTemplate restTemplate;

    @Autowired
    public GeocodingLoader(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Override
    public Optional<Object> getCityCoordinate(String cityName) {
        String url = HTTP + Geocoding_URL + QUESTION +
                CITY_NAME_QUERY_PARAM + EQUAL + cityName + AND +
                API_KEY_QUERY_PARAM + EQUAL + API_KEY;
        Optional<Object> answer = Optional.ofNullable(restTemplate.getForObject(url, Object.class));
        if (answer.isPresent()) {
            LOGGER.info(String.valueOf(answer.get()));
            return answer;
        }

        return Optional.empty();
    }

    public Object getResponse(ObjectNode objectNode) {
        Optional<Object> result = getCityCoordinate(objectNode.get("city").asText());

        return result.orElse(null);
    }
}
