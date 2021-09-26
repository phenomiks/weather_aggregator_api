package ru.geekbrains.api.dispatcher.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.geekbrains.api.dispatcher.application.utils.JsonResponseGenerator;
import ru.geekbrains.api.dispatcher.application.utils.ValidateRequestUtils;


@RestController
@RequestMapping(value = "/api/v1/dispatcher")
public class DispatcherController {
    private final ValidateRequestUtils validateRequestUtils;

    public DispatcherController(ValidateRequestUtils validateRequestUtils) {
        this.validateRequestUtils = validateRequestUtils;
    }

    //    http://localhost:8090/api/v1/dispatcher/register
    @PostMapping(value = "/register")
    public ResponseEntity<?> registerUser(@RequestBody ObjectNode json) {
        validateRequestUtils.validateUserRegistrationParameters(json);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(json.toString(), headers);
        ObjectNode response =
                restTemplate.postForObject("http://localhost:8092/api/v1/auth/register", request, ObjectNode.class);
        return ResponseEntity.ok(response);
    }

    //    http://localhost:8090/api/v1/dispatcher/registerDemo
    @PostMapping(value = "/registerDemo")
    public ResponseEntity<?> registerUserDemo(@RequestBody ObjectNode json) {
        validateRequestUtils.validateUserRegistrationParameters(json);

        ObjectNode response = JsonResponseGenerator.generateSuccessResponseJson();
        return ResponseEntity.ok(response);
    }

    //    http://localhost:8090/api/v1/dispatcher/get-weather
        @PostMapping(value = "/get-weather")
    public ResponseEntity<?> getWeather(@RequestBody ObjectNode json) {

            ObjectNode response = JsonResponseGenerator.generateSuccessResponseJson();
        return ResponseEntity.ok(response);
    }
}
