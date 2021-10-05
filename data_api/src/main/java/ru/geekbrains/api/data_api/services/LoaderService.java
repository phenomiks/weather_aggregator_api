package ru.geekbrains.api.data_api.services;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.geekbrains.api.data_api.application.utils.ExceptionHandlerForRestService;

@Service
public class LoaderService {

    @Value("${loader.getWeather}")
    private String url;

    private final RestService restService;

    @Autowired
    public LoaderService(RestService restService) {
        this.restService = restService;
    }

    public ResponseEntity<?> LoaderWeather(ObjectNode json){
        ResponseEntity<?> responseEntity = restService.doPost(json, url);
        if (responseEntity == null){
            ExceptionHandlerForRestService.timeOutException();
        }
        return responseEntity;
    }
}
