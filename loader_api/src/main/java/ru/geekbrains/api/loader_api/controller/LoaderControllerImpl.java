package ru.geekbrains.api.loader_api.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.api.loader_api.controller.interfaces.LoaderController;
import ru.geekbrains.api.loader_api.service.OpenWeatherLoader;

@RestController
public class LoaderControllerImpl implements LoaderController {
    private final OpenWeatherLoader openWeatherLoader;

    @Autowired
    public LoaderControllerImpl(OpenWeatherLoader openWeatherLoader) {
        this.openWeatherLoader = openWeatherLoader;
    }

    @Override
    public ResponseEntity<?> getWeatherByCity(@RequestBody ObjectNode objectNode) {
        return openWeatherLoader.getResponse(objectNode);
    }
}
