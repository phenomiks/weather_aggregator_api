package ru.geekbrains.front.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpClient {
    private final RestTemplate restTemplate;

    @Autowired
    public HttpClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<ObjectNode> doPost(String model, String url) {
        HttpEntity<String> entity = getHttpEntity(model);

        try {
            return restTemplate.postForEntity(url, entity, ObjectNode.class);
        } catch (ResourceAccessException e) {
            return null;
        }
    }

    private HttpEntity<String> getHttpEntity(String model) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(model, httpHeaders);
    }
}


