package ru.geekbrains.api.dispatcher.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import ru.geekbrains.api.dispatcher.application.utils.ExceptionHandlerForPostRequester;
import ru.geekbrains.api.dispatcher.application.utils.ValidateRequestUtils;
import ru.geekbrains.api.dispatcher.service.AuthService;
import ru.geekbrains.api.dispatcher.service.DataService;


@RestController
@RequestMapping(value = "/api/v1/dispatcher")
public class DispatcherController {
    private final ValidateRequestUtils validateRequestUtils;
    private final AuthService authService;
    private final DataService dataService;

    public DispatcherController(ValidateRequestUtils validateRequestUtils, AuthService authService, DataService dataService) {
        this.validateRequestUtils = validateRequestUtils;
        this.authService = authService;
        this.dataService = dataService;
    }

    //    http://localhost:8090/api/v1/dispatcher/register
    @PostMapping(value = "/register")
    public ResponseEntity<?> registerUser(@RequestBody ObjectNode json) {
        try {
            return authService.registerUser(json);
        } catch (HttpStatusCodeException e) {
           return ExceptionHandlerForPostRequester.checkResponseHttpStatus(e);
        }
    }

    //    http://localhost:8090/api/v1/dispatcher/get-weather
    @PostMapping(value = "/get-weather")
    public ResponseEntity<?> getWeather(@RequestBody ObjectNode json) {
        validateRequestUtils.validateUserRegistrationParameters(json);
        try {
            return dataService.getWeather(json);
        } catch (HttpStatusCodeException e) {
            return ExceptionHandlerForPostRequester.checkResponseHttpStatus(e);
        }
    }
}
