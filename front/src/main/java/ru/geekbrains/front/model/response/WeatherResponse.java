package ru.geekbrains.front.model.response;

public class WeatherResponse {
    private final String firstField;
    private final String secondField;

    public WeatherResponse(String firstField, String secondField) {
        this.firstField = firstField;
        this.secondField = secondField;
    }

    public String getFirstField() {
        return firstField;
    }

    public String getSecondField() {
        return secondField;
    }
}
