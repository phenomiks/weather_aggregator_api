package ru.geekbrains.api.dispatcher.controller.interfaces;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/api/v1/dispatcher")
public interface DispatcherController {

    @PostMapping(value = "/register")
    ResponseEntity<?> registerUser(@RequestBody ObjectNode json);

    @PostMapping(value = "/new-key")
    ResponseEntity<?> getNewKey(@RequestBody ObjectNode json);

    @PostMapping(value = "/user-keys")
    ResponseEntity<?> getUserKeys(@RequestBody ObjectNode json);

    @PostMapping(value = "/get-weather")
    ResponseEntity<?> getWeather(@RequestBody ObjectNode json);
}
