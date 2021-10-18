package ru.geekbrains.api.loader_api.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.PropertySource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ru.geekbrains.api.loader_api.domain.City;
import ru.geekbrains.api.loader_api.exception.CityNotFoundException;
import ru.geekbrains.api.loader_api.exception.RequiredParamNotFound;

import java.util.Objects;
import java.util.Optional;

import static ru.geekbrains.api.loader_api.exception.ErrorCodes.CITY_NOT_FOUND;
import static ru.geekbrains.api.loader_api.utils.HttpRequestConstants.HTTP;
import static ru.geekbrains.api.loader_api.utils.OpenWeatherRequestConstants.*;

@Service
@PropertySource("classpath:private.properties")
public class GeocodingLoaderServiceImpl implements GeoLoaderService {

    @Value("${api.key.open.weather}")
    private String API_KEY;

    private final Logger LOGGER = LoggerFactory.getLogger(GeocodingLoaderServiceImpl.class);

    private final RestTemplate restTemplate;
    private final CacheManager cacheManager;

    @Autowired
    public GeocodingLoaderServiceImpl(RestTemplateBuilder builder, CacheManager cacheManager) {
        this.cacheManager = cacheManager;
        this.restTemplate = builder.build();
    }

    public City getCity(String cityName) {
        City city = findInCacheCity(cityName);

        if (city != null) {
            return city;
        }

        UriComponents urlBuilder;
        urlBuilder = UriComponentsBuilder.newInstance()
                .scheme(HTTP).host(GEOCODING_URL)
                .queryParam(API_KEY_QUERY_PARAM, API_KEY)
                .queryParam(CITY_NAME_QUERY_PARAM, cityName)
                .build();
        Optional<City[]> answer = Optional.ofNullable(restTemplate.getForObject(urlBuilder.toUriString(), City[].class));
        if (answer.isPresent()) {
            if (answer.get().length > 0) {
                LOGGER.info("Response: " + answer.get()[0].toString());
                //It works if the answer has the first correct city
                city = answer.get()[0];
                city.setName(cityName);
                putInCacheCity(city);
                return answer.get()[0];
            }
        }

        throw new CityNotFoundException(CITY_NOT_FOUND.getMessage());
    }

    public void putInCacheCity(City city) {
        if (city.getName() == null) {
            throw new RequiredParamNotFound("city name");
        }
        Objects.requireNonNull(cacheManager.getCache("cities")).put(city.getName(), city);
    }

    @SuppressWarnings("ConstantConditions")
    @Nullable
    public City findInCacheCity(String cityName) {
        if (cityName == null) {
            throw new RequiredParamNotFound("city name");
        }
        try {
            return (City) cacheManager.getCache("cities").get(cityName).get();
        } catch (NullPointerException | ClassCastException e) {
            return null;
        }
    }

    public Optional<City> getResponse(ObjectNode objectNode) {
        return Optional.ofNullable(getCity(objectNode.get("city").asText()));
    }
}
