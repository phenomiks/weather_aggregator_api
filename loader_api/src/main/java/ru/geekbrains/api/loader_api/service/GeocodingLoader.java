package ru.geekbrains.api.loader_api.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ru.geekbrains.api.loader_api.domain.City;
import ru.geekbrains.api.loader_api.exception.CityNotFoundException;
import ru.geekbrains.api.loader_api.exception.LoaderApiException;
import ru.geekbrains.api.loader_api.exception.RequiredParamNotFound;

import java.util.Arrays;
import java.util.Optional;

import static ru.geekbrains.api.loader_api.exception.ErrorCodes.CITY_NOT_FOUND;
import static ru.geekbrains.api.loader_api.utils.HttpRequestConstants.HTTP;
import static ru.geekbrains.api.loader_api.utils.OpenWeatherRequestConstants.*;

@Service
@PropertySource("classpath:private.properties")
public class GeocodingLoader implements GeoLoader {

    @Value("${api.key.open.weather}")
    private String API_KEY;

    private Logger LOGGER = LoggerFactory.getLogger(GeocodingLoader.class);

    private RestTemplate restTemplate;

    @Autowired
    public GeocodingLoader(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Cacheable("cities")
    public City getCity(String cityName) throws LoaderApiException{
        UriComponents urlBuilder;
        urlBuilder = UriComponentsBuilder.newInstance()
                .scheme(HTTP).host(GEOCODING_URL)
                .queryParam(API_KEY_QUERY_PARAM, API_KEY)
                .queryParam(CITY_NAME_QUERY_PARAM, cityName)
                .build();
        Optional<City[]> answer = Optional.ofNullable(restTemplate.getForObject(urlBuilder.toUriString(), City[].class));
        if (answer.isPresent()) {
            if (answer.get().length > 0) {
                LOGGER.info(Arrays.toString(answer.get()));
                //It works if the answer has the first correct city
                updateCity(answer.get()[0]);
                return answer.get()[0];
            }
        }

        throw new CityNotFoundException(CITY_NOT_FOUND.getMessage());
    }


    @CachePut(value = "city", key = "#city.name")
    public City updateCity(City city) {
        if (city.getName() == null) {
            throw new RequiredParamNotFound("city name");
        }

        return city;
    }

    public Optional<City> getResponse(ObjectNode objectNode) {
        return Optional.ofNullable(getCity(objectNode.get("city").asText()));
    }
}
