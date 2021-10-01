package ru.geekbrains.api.loader_api.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.api.loader_api.service.OpenWeatherLoader;

@RequestMapping("/api/v1/loader")
@RestController
public class LoaderController {

    private OpenWeatherLoader openWeatherLoader;

    @Autowired
    public LoaderController(OpenWeatherLoader openWeatherLoader) {
        this.openWeatherLoader = openWeatherLoader;
    }

    @PostMapping
    ResponseEntity<?> getWeatherByCity(@RequestBody ObjectNode objectNode) {
        return openWeatherLoader.getResponse(objectNode);
    }
}
