package ru.geekbrains.api.dispatcher.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Component
public class PostRequester {

    private final RestTemplate restTemplate;

    @Autowired
    public PostRequester(RestTemplate restTemplate) {
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
