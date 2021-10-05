package ru.geekbrains.api.data_api.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import ru.geekbrains.api.data_api.application.utils.ExceptionHandlerForRestService;
import ru.geekbrains.api.data_api.application.utils.ValidateRequestUtils;
import ru.geekbrains.api.data_api.services.LoaderService;


@RestController
public class DataControllerImpl implements DataController {
    private final ValidateRequestUtils validateRequestUtils;
    private final LoaderService loaderService;

    @Autowired
    public DataControllerImpl(ValidateRequestUtils validateRequestUtils, LoaderService loaderService) {
        this.validateRequestUtils = validateRequestUtils;
        this.loaderService = loaderService;
    }

    @Override
    public ResponseEntity<?> getWeather(@RequestBody ObjectNode json) {
        validateRequestUtils.validateUserRegistrationParameters(json);
        try {
            return loaderService.loaderWeather(json);
        } catch (HttpStatusCodeException e) {
            return ExceptionHandlerForRestService.checkResponseHttpStatus(e);
        }
    }
}
