package ru.geekbrains.api.dispatcher.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.geekbrains.api.dispatcher.application.utils.ExceptionHandlerForPostRequester;

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
        ResponseEntity<?> responseEntity = postRequester.doPost(json, urlGetWeather);
        if (responseEntity == null){
            ExceptionHandlerForPostRequester.timeOutException();
        }
        return responseEntity;
    }
}
