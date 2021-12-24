package ru.geekbrains.api.dispatcher.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.geekbrains.api.dispatcher.utils.ExceptionHandlerForPostRequester;
import ru.geekbrains.api.dispatcher.utils.TokenValidator;

@Service
public class DataService {

    @Value("${dataApi.getWeather}")
    private String urlGetWeather;

    private final PostRequester postRequester;
    private final TokenValidator tokenValidator;

    @Autowired
    public DataService(PostRequester postRequester, TokenValidator tokenValidator) {
        this.postRequester = postRequester;
        this.tokenValidator = tokenValidator;
    }

    public ResponseEntity<?> getWeather(ObjectNode json, String key) {
        tokenValidator.checkException(key);
        json.remove("key");
        ResponseEntity<?> responseEntity = postRequester.doPost(json, urlGetWeather);
        if (responseEntity == null) {
            ExceptionHandlerForPostRequester.timeOutException();
        }
        return responseEntity;
    }
}
