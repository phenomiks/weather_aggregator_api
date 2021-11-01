package ru.geekbrains.front.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/v1/front-weather")
public class FrontControllerImpl {

//    http://localhost:8095/api/v1/front-weather/index
    @GetMapping(value = "/index")
    public String getIndexPage() {
        return "index";
    }

//    http://localhost:8095/api/v1/front-weather/api-page
    @GetMapping(value = "/api-page")
    public String getApiPage(){
        return "api";
    }

//    dispatcher endpoint: http://localhost:8090/api/v1/dispatcher/get-weather
    @GetMapping(value = "/get-weather")
    public String getWeather(@RequestParam(value = "city", required = false) String city){
        System.out.println(city);
        return "weather";
    }

}
