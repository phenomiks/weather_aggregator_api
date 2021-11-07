package ru.geekbrains.front.model.response;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class WeatherResponse {

    public OpenweatherEntity owResponse(ObjectNode json){
        OpenweatherEntity openweather = new OpenweatherEntity(json);
        openweather.fillWeatherEntity();
        return openweather;
    }

    public YandexweatherEntity ywResponse(ObjectNode json){
        YandexweatherEntity yandexweather = new YandexweatherEntity(json);
        yandexweather.fillWeatherEntity();
        return yandexweather;
    }
}
