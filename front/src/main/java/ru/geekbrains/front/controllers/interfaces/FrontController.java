package ru.geekbrains.front.controllers.interfaces;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(value = "/api/v1/front-weather")
public interface FrontController {


    @GetMapping(value = "/index")
    String index();

    @GetMapping(value = "/api-page")
    String getApiPage();

    @PostMapping(value = "/get-weather")
    String getWeather(@RequestParam(value = "city", required = false) String city);
//
//    @PostMapping(value = "/register")
//    ResponseEntity<?> registerUser(@RequestBody ObjectNode json);
//
//    @PostMapping(value = "/get-weather")
//    ResponseEntity<?> getWeather(@RequestBody ObjectNode json);

}
