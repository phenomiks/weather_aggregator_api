package ru.geekbrains.api.dispatcher.controller.interfaces;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/api/v1/dispatcher")
public interface DispatcherAbstractController {

    @PostMapping(value = "/register")
    ResponseEntity<?> registerUser(@RequestBody ObjectNode json);

    @PostMapping(value = "/get-weather")
    ResponseEntity<?> getWeather(@RequestBody ObjectNode json);
}
