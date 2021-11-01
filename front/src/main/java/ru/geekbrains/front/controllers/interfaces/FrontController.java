package ru.geekbrains.front.controllers.interfaces;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(value = "/front-weather")
public interface FrontController {


    @GetMapping(value = "/index")
    String getIndexPage();

    @GetMapping(value = "/api-page")
    String getApiPage();

    @GetMapping(value = "/getWeather")
    String getWeather(@RequestParam(value = "city", required = false) String city);
}
