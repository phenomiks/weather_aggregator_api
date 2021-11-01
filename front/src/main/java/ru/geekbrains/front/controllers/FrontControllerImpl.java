package ru.geekbrains.front.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Controller
@RequestMapping("/api/v1/front-weather")
public class FrontControllerImpl {

    private final RestTemplate restTemplate;
    @Value("${dispatcherApi.getWeather}")
    private String dispatcherWeatherUrl;

    @Autowired
    public FrontControllerImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

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

    @GetMapping(value = "/get-weather")
    public String getWeather(@RequestParam(value = "city", required = false) String city) {
//        System.out.println(city);
//        WeatherRequestForm request = new WeatherRequestForm(city, new String[]{"openweather", "yandexweather"}, false, "asdavxcv4sdfvx.asdas32rsdfxvxc.asdsdfdsfd");

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("city", city);
        objectNode.put("services", objectMapper.convertValue(Arrays.asList("openweather", "yandexweather"), JsonNode.class));
        objectNode.put("needDetailed", false);
        objectNode.put("key", getKey());

        System.out.println(objectNode);
        System.out.println(restTemplate.postForEntity("http://localhost:8090/api/v1/dispatcher/get-weather", objectNode, ObjectNode.class));

        return "weather";
    }

    private String getKey(){
        return "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MiIsImlhdCI6MTYzNTc3MjU4MCwiZXhwIjoxNjM1Nzc4NTgwfQ.eU1aSpm75h2TImxgGpl2EsRFSbFnnxSEoaV4xCJuLHM";
    }

}
