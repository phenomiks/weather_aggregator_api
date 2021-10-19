package ru.geekbrains.api.data_api.model;

public enum WeatherService {
    OPEN_WEATHER("openweather"),
    YANDEX_WEATHER("yandexweather");

    private final String name;

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
