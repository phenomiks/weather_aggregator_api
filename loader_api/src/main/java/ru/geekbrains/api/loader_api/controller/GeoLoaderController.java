package ru.geekbrains.api.loader_api.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.api.loader_api.service.GeocodingLoader;

@RestController
@RequestMapping("/api/v1/loader/geo/")
public class GeoLoaderController {
    private final GeocodingLoader geocodingLoader;

    @Autowired
    public GeoLoaderController(GeocodingLoader geocodingLoader) {
        this.geocodingLoader = geocodingLoader;
    }

    @PostMapping("/get-coordinate")
    public ResponseEntity<Object> getCityCoordinate(@RequestBody ObjectNode city) {
        Object response = geocodingLoader.getResponse(city);

        return ResponseEntity.ok(response);
    }
}
