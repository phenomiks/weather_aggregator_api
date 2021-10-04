package ru.geekbrains.api.dispatcher.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import ru.geekbrains.api.dispatcher.config.RestTemplateConfig;

@Component
public class PostRequester {

    private final RestTemplate restTemplate;

    @Autowired
    public PostRequester(RestTemplateConfig restTemplateConfig) {
        this.restTemplate = restTemplateConfig.restTemplate();
    }

    public ResponseEntity<?> doPost(ObjectNode json, String url) {
        try {
            return restTemplate.postForEntity(url, json, ObjectNode.class);
        } catch (HttpClientErrorException he) {
            return new ResponseEntity<>(he.getResponseBodyAsString(), he.getStatusCode());
        } catch (ResourceAccessException e) {
            return new ResponseEntity<>("CONNECTION_REFUSED", HttpStatus.valueOf(409));
        }
    }
}
