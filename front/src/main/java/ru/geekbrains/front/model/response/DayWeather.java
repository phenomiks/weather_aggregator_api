package ru.geekbrains.front.model.response;

public class DayWeather extends CurrentWeather {
    private Integer feelTemperature;

    public DayWeather(Integer temperature, Double windSpeed, Integer humidity, Integer pressure, String condition,
                      Integer feelTemperature) {
        super(temperature, windSpeed, humidity, pressure, condition);
        this.feelTemperature = feelTemperature;
    }

    public Integer getFeelTemperature() {
        return feelTemperature;
    }

    public void setFeelTemperature(Integer feelTemperature) {
        this.feelTemperature = feelTemperature;
    }
}
