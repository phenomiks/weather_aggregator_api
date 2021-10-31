package ru.geekbrains.api.loader_api.domain;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class Report {
    private ObjectNode openWeather;
    private ObjectNode yandexWeather;

    public Report() {
    }

    public ObjectNode getOpenWeather() {
        return openWeather;
    }

    public ObjectNode getYandexWeather() {
        return yandexWeather;
    }

    public void setOpenWeather(ObjectNode openWeather) {
        this.openWeather = openWeather;
    }

    public void setYandexWeather(ObjectNode yandexWeather) {
        this.yandexWeather = yandexWeather;
    }
}
