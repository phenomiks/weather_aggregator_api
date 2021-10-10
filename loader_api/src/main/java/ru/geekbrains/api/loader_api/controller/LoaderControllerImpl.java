package ru.geekbrains.api.loader_api.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import ru.geekbrains.api.loader_api.controller.interfaces.LoaderController;
import ru.geekbrains.api.loader_api.exception.ErrorCodes;
import ru.geekbrains.api.loader_api.exception.LoaderApiException;
import ru.geekbrains.api.loader_api.service.OpenWeatherLoader;
import ru.geekbrains.api.loader_api.utils.JsonResponseGenerator;

@RestController
public class LoaderControllerImpl implements LoaderController {
    private final OpenWeatherLoader openWeatherLoader;

    @Autowired
    public LoaderControllerImpl(OpenWeatherLoader openWeatherLoader) {
        this.openWeatherLoader = openWeatherLoader;
    }

    @Override
    public ResponseEntity<?> getWeatherByCity(@RequestBody ObjectNode objectNode) {
        try {
            return openWeatherLoader.getResponse(objectNode);
        } catch (HttpStatusCodeException e) {
            ObjectNode body = JsonResponseGenerator.generateErrorResponseJson(ErrorCodes.INTERNAL_ERROR, "");

            throw new LoaderApiException("Error from the OpenWeather service", body);
        }
    }
}
