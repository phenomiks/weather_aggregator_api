package ru.geekbrains.api.dispatcher.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.geekbrains.api.dispatcher.utils.ExceptionHandlerForPostRequester;
import ru.geekbrains.api.dispatcher.utils.JwtUtils;

@Service
public class DataService {

    @Value("${dataApi.getWeather}")
    private String urlGetWeather;

    private final PostRequester postRequester;

    private final JwtUtils jwtUtils;

    @Autowired
    public DataService(PostRequester postRequester, JwtUtils jwtUtils) {
        this.postRequester = postRequester;
        this.jwtUtils = jwtUtils;
    }

    public ResponseEntity<?> getWeather(ObjectNode json, String key) {
//        try {
//            jwtUtils.getUsernameFromToken(key);
//        } catch (MalformedJwtException e) {
//
//        } catch (ExpiredJwtException e) {
//
//        }
        json.remove("key");
        ResponseEntity<?> responseEntity = postRequester.doPost(json, urlGetWeather);
        if (responseEntity == null) {
            ExceptionHandlerForPostRequester.timeOutException();
        }
        return responseEntity;
    }
}
