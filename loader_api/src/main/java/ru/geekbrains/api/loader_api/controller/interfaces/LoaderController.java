package ru.geekbrains.api.loader_api.controller.interfaces;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/loader")
public interface LoaderController {
    @PostMapping("/get-weather")
    ResponseEntity<?> getWeatherByCity(@RequestBody ObjectNode objectNode);
}
