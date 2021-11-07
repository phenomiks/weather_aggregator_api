package ru.geekbrains.front.model.response;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ru.geekbrains.front.utils.WeatherUtil;

public class OpenweatherEntity {
    private ObjectNode weatherJson;

    private String currentTemperature;
    private String morningTemperature;
    private String dayTemperature;
    private String eveningTemperature;
    private String nightTemperature;

    private String feelMorningTemperature;
    private String feelDayTemperature;
    private String feelEveningTemperature;
    private String feelNightTemperature;

    private String currentWindSpeed;
    private String dailyWindSpeed;

    private String currentHumidity;
    private String dailyHumidity;

    private String currentPressure;
    private String dailyPressure;

    private String currentCondition;
    private String dailyCondition;

    public OpenweatherEntity(ObjectNode json) {
        this.weatherJson = json;
    }

    public void fillWeatherEntity(){
        JsonNode jsonNode = weatherJson.get("report").get("openweather");
        getCurrentWeather(jsonNode.get("current"));
        getDailyWeather(jsonNode.get("daily").get(0));
    }

    private void getCurrentWeather(JsonNode json){
        currentTemperature = WeatherUtil.convertTemperature(json.get("temp").asDouble()) + " °C";
        currentWindSpeed = json.get("wind_speed").asText() + " m/s";
        currentHumidity = json.get("humidity").asText() + " %";
        currentPressure = json.get("pressure").asText() + " hPa";
        currentCondition = json.get("weather").get(0).get("description").asText();
    }

    private void getDailyWeather(JsonNode json){
        morningTemperature = WeatherUtil.convertTemperature(json.get("temp").get("morn").asDouble());
        dayTemperature = WeatherUtil.convertTemperature(json.get("temp").get("day").asDouble());
        eveningTemperature = WeatherUtil.convertTemperature(json.get("temp").get("eve").asDouble());
        nightTemperature = WeatherUtil.convertTemperature(json.get("temp").get("night").asDouble());

        feelMorningTemperature = WeatherUtil.convertTemperature(json.get("feels_like").get("morn").asDouble());
        feelDayTemperature = WeatherUtil.convertTemperature(json.get("feels_like").get("day").asDouble());
        feelEveningTemperature = WeatherUtil.convertTemperature(json.get("feels_like").get("eve").asDouble());
        feelNightTemperature = WeatherUtil.convertTemperature(json.get("feels_like").get("night").asDouble());

        dailyWindSpeed = json.get("wind_speed").asText();
        dailyHumidity = json.get("humidity").asText();
        dailyPressure = json.get("pressure").asText();
        dailyCondition = json.get("weather").get(0).get("description").asText();
    }

    public String getCurrentTemperature() {
        return currentTemperature;
    }

    public String getMorningTemperature() {
        return morningTemperature;
    }

    public String getDayTemperature() {
        return dayTemperature;
    }

    public String getEveningTemperature() {
        return eveningTemperature;
    }

    public String getNightTemperature() {
        return nightTemperature;
    }

    public String getFeelMorningTemperature() {
        return feelMorningTemperature;
    }

    public String getFeelDayTemperature() {
        return feelDayTemperature;
    }

    public String getFeelEveningTemperature() {
        return feelEveningTemperature;
    }

    public String getFeelNightTemperature() {
        return feelNightTemperature;
    }

    public String getCurrentWindSpeed() {
        return currentWindSpeed;
    }

    public String getDailyWindSpeed() {
        return dailyWindSpeed;
    }

    public String getCurrentHumidity() {
        return currentHumidity;
    }

    public String getDailyHumidity() {
        return dailyHumidity;
    }

    public String getCurrentPressure() {
        return currentPressure;
    }

    public String getDailyPressure() {
        return dailyPressure;
    }

    public String getCurrentCondition() {
        return currentCondition;
    }

    public String getDailyCondition() {
        return dailyCondition;
    }

}
