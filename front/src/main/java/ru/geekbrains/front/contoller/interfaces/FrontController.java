package ru.geekbrains.front.contoller.interfaces;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface FrontController {
    @GetMapping(value = "/")
    String getIndexPage(Model model);

    @GetMapping(value = "/api")
    String getApiPage();

    @PostMapping(value = "/weather")
    String requestWeather(@RequestBody String cityParam, Model model);
}
