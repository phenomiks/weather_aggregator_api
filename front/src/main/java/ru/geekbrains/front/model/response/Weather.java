package ru.geekbrains.front.model.response;

import java.util.Map;

public class Weather {
    private CurrentWeather currentWeather;
    private Map<String, DayWeather> dayWeather;

    public Weather(CurrentWeather currentWeather, Map<String, DayWeather> dayWeather) {
        this.currentWeather = currentWeather;
        this.dayWeather = dayWeather;
    }

    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(CurrentWeather currentWeather) {
        this.currentWeather = currentWeather;
    }

    public Map<String, DayWeather> getDayWeather() {
        return dayWeather;
    }

    public void setDayWeather(Map<String, DayWeather> dayWeather) {
        this.dayWeather = dayWeather;
    }
}
