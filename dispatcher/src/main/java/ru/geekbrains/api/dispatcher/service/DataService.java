package ru.geekbrains.api.dispatcher.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DataService {

    @Value("${dataApi.getWeather}")
    private String urlGetWeather;

    private final PostRequester postRequester;

    @Autowired
    public DataService(PostRequester postRequester) {
        this.postRequester = postRequester;
    }

    public ResponseEntity<?> getWeather(ObjectNode json){
        json.remove("key");
        return postRequester.doPost(json, urlGetWeather);
    }
}
