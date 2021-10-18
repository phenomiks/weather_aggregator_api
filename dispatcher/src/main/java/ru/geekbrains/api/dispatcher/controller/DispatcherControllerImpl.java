package ru.geekbrains.api.dispatcher.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import ru.geekbrains.api.dispatcher.utils.ExceptionHandlerForPostRequester;
import ru.geekbrains.api.dispatcher.utils.ValidateRequestUtils;
import ru.geekbrains.api.dispatcher.controller.interfaces.DispatcherController;
import ru.geekbrains.api.dispatcher.service.AuthService;
import ru.geekbrains.api.dispatcher.service.DataService;


@RestController
public class DispatcherControllerImpl implements DispatcherController {
    private final ValidateRequestUtils validateRequestUtils;
    private final AuthService authService;
    private final DataService dataService;

    public DispatcherControllerImpl(ValidateRequestUtils validateRequestUtils, AuthService authService, DataService dataService) {
        this.validateRequestUtils = validateRequestUtils;
        this.authService = authService;
        this.dataService = dataService;
    }

    //    http://localhost:8090/api/v1/dispatcher/register
    @Override
    public ResponseEntity<?> registerUser(@RequestBody ObjectNode json) {
        try {
            return authService.registerUser(json);
        } catch (HttpStatusCodeException e) {
           return ExceptionHandlerForPostRequester.checkResponseHttpStatus(e);
        }
    }

    //    http://localhost:8090/api/v1/dispatcher/new-key
    @Override
    public ResponseEntity<?> getNewKey(ObjectNode json) {
        try {
            return authService.getNewKey(json);
        } catch (HttpStatusCodeException e) {
            return ExceptionHandlerForPostRequester.checkResponseHttpStatus(e);
        }
    }

    //    http://localhost:8090/api/v1/dispatcher/user-keys
    @Override
    public ResponseEntity<?> getUserKeys(ObjectNode json) {
        try {
            return authService.getUserKeys(json);
        } catch (HttpStatusCodeException e) {
            return ExceptionHandlerForPostRequester.checkResponseHttpStatus(e);
        }
    }

    //    http://localhost:8090/api/v1/dispatcher/get-weather
    @Override
    public ResponseEntity<?> getWeather(@RequestBody ObjectNode json) {
        String key = validateRequestUtils.validateUserRegistrationParameters(json);
        try {
            return dataService.getWeather(json, key);
        } catch (HttpStatusCodeException e) {
            return ExceptionHandlerForPostRequester.checkResponseHttpStatus(e);
        }
    }
}
