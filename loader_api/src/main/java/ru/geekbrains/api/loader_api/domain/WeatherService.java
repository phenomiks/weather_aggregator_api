package ru.geekbrains.api.loader_api.domain;

public enum WeatherService {
    OPEN_WEATHER("openweather"),
    YANDEX_WEATHER("yandexweather");

    private String name;

    WeatherService(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "WeatherService{" +
                "name='" + name + '\'' +
                '}';
    }
}
