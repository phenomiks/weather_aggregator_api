package ru.geekbrains.front.contoller.interfaces;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface FrontController {
    @GetMapping
    String getIndexPage(Model model);

    @GetMapping(value = "/api")
    String getApiPage();

    @GetMapping(value = "/getWeather")
    String getWeather(@RequestParam(value = "city") String city, Model model);
}
