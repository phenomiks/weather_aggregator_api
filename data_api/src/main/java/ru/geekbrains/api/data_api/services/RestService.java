package ru.geekbrains.api.data_api.services;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import ru.geekbrains.api.data_api.config.RestTemplateConfig;

@Component
public class RestService {

    private final RestTemplate restTemplate;

    @Autowired
    public RestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<?> doPost(ObjectNode json, String url) {
        try {
            return restTemplate.postForEntity(url, json, ObjectNode.class);
        } catch (ResourceAccessException e) {
            return null;
        }
    }
}
