package ru.geekbrains.api.loader_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.api.loader_api.service.OpenWeatherLoader;

@RequestMapping("/api/loader")
@RestController
public class LoaderController {

    private OpenWeatherLoader openWeatherLoader;

    @Autowired
    public LoaderController(OpenWeatherLoader openWeatherLoader) {
        this.openWeatherLoader = openWeatherLoader;
    }

    @RequestMapping(method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> getWeatherByCity(@RequestParam String cityName) {
        return ResponseEntity.of(openWeatherLoader.getByCityName(cityName));
    }
}
