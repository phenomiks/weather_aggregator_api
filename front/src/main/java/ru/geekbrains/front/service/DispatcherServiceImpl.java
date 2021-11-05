package ru.geekbrains.front.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.geekbrains.front.model.WeatherService;
import ru.geekbrains.front.model.request.WeatherRequestForm;
import ru.geekbrains.front.service.interfaces.DispatcherService;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DispatcherServiceImpl implements DispatcherService {
    private final HttpClient httpClient;

    @Value("${dispatcherApi.getWeather}")
    private String url;

    @Value("${jwt.key}")
    private String key;

    @Autowired
    public DispatcherServiceImpl(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public ObjectNode getWeather(String city) {
        WeatherRequestForm requestForm = getWeatherRequestForm(city);
        String request = getRequestString(requestForm);
        if (request == null) {
            throw new IllegalArgumentException("Request is null"); // TODO return an empty response
        }

        ResponseEntity<ObjectNode> response = httpClient.doPost(request, url);
        if (response == null) {
            throw new IllegalArgumentException("Response is null"); // TODO return an empty response
        }

        return response.getBody();
    }

    private WeatherRequestForm getWeatherRequestForm(String city) {
        Set<String> services = Arrays.stream(WeatherService.values())
                .map(WeatherService::getName)
                .collect(Collectors.toSet());

        return new WeatherRequestForm(city, services, false, key);
    }

    private String getRequestString(WeatherRequestForm requestForm) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(requestForm);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
