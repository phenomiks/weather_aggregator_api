package ru.geekbrains.front.model.response;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ru.geekbrains.front.utils.WeatherUtil;

public class YandexweatherEntity {
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
    private String morningWindSpeed;
    private String dayWindSpeed;
    private String eveningWindSpeed;
    private String nightWindSpeed;

    private String currentHumidity;
    private String morningHumidity;
    private String dayHumidity;
    private String eveningHumidity;
    private String nightHumidity;

    private String currentPressure;
    private String morningPressure;
    private String dayPressure;
    private String eveningPressure;
    private String nightPressure;

    private String currentCondition;
    private String morningCondition;
    private String dayCondition;
    private String eveningCondition;
    private String nightCondition;

    public YandexweatherEntity(ObjectNode json) {
        this.weatherJson = json;
    }

    public void fillWeatherEntity(){
        JsonNode jsonNode = weatherJson.get("report").get("yandexweather");
        getCurrentWeather(jsonNode.get("fact"));
        getMorningWeather(jsonNode.get("forecasts").get(0).get("parts").get("morning"));
        getDayWeather(jsonNode.get("forecasts").get(0).get("parts").get("day"));
        getEveningWeather(jsonNode.get("forecasts").get(0).get("parts").get("evening"));
        getNightWeather(jsonNode.get("forecasts").get(0).get("parts").get("night"));
    }

    private void getCurrentWeather(JsonNode json){
        currentTemperature = WeatherUtil.convertTemperature(json.get("temp").asInt()) + " °C";
        currentWindSpeed = json.get("wind_speed").asText() + " m/s";
        currentHumidity = json.get("humidity").asText() + " %";
        currentPressure = json.get("pressure_mm").asText() + " mm Hg";
        currentCondition = json.get("condition").asText();
    }

    private void getMorningWeather(JsonNode json){
        morningTemperature = WeatherUtil.convertTemperature(json.get("temp_avg").asInt()) + " °C";
        morningWindSpeed = json.get("wind_speed").asText();
        morningHumidity = json.get("humidity").asText();
        morningPressure = json.get("pressure_mm").asText();
        morningCondition = json.get("condition").asText();
        feelMorningTemperature = WeatherUtil.convertTemperature(json.get("feels_like").asInt());
    }

    private void getDayWeather(JsonNode json){
        dayTemperature = WeatherUtil.convertTemperature(json.get("temp_avg").asInt()) + " °C";
        dayWindSpeed = json.get("wind_speed").asText();
        dayHumidity = json.get("humidity").asText();
        dayPressure = json.get("pressure_mm").asText();
        dayCondition = json.get("condition").asText();
        feelDayTemperature = WeatherUtil.convertTemperature(json.get("feels_like").asInt());
    }

    private void getEveningWeather(JsonNode json){
        eveningTemperature = WeatherUtil.convertTemperature(json.get("temp_avg").asInt()) + " °C";
        eveningWindSpeed = json.get("wind_speed").asText();
        eveningHumidity = json.get("humidity").asText();
        eveningPressure = json.get("pressure_mm").asText();
        eveningCondition = json.get("condition").asText();
        feelEveningTemperature = WeatherUtil.convertTemperature(json.get("feels_like").asInt());
    }

    private void getNightWeather(JsonNode json){
        nightTemperature = WeatherUtil.convertTemperature(json.get("temp_avg").asInt()) + " °C";
        nightWindSpeed = json.get("wind_speed").asText();
        nightHumidity = json.get("humidity").asText();
        nightPressure = json.get("pressure_mm").asText();
        nightCondition = json.get("condition").asText();
        feelNightTemperature = WeatherUtil.convertTemperature(json.get("feels_like").asInt());
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

    public String getMorningWindSpeed() {
        return morningWindSpeed;
    }

    public String getDayWindSpeed() {
        return dayWindSpeed;
    }

    public String getEveningWindSpeed() {
        return eveningWindSpeed;
    }

    public String getNightWindSpeed() {
        return nightWindSpeed;
    }

    public String getCurrentHumidity() {
        return currentHumidity;
    }

    public String getMorningHumidity() {
        return morningHumidity;
    }

    public String getDayHumidity() {
        return dayHumidity;
    }

    public String getEveningHumidity() {
        return eveningHumidity;
    }

    public String getNightHumidity() {
        return nightHumidity;
    }

    public String getCurrentPressure() {
        return currentPressure;
    }

    public String getMorningPressure() {
        return morningPressure;
    }

    public String getDayPressure() {
        return dayPressure;
    }

    public String getEveningPressure() {
        return eveningPressure;
    }

    public String getNightPressure() {
        return nightPressure;
    }

    public String getCurrentCondition() {
        return currentCondition;
    }

    public String getMorningCondition() {
        return morningCondition;
    }

    public String getDayCondition() {
        return dayCondition;
    }

    public String getEveningCondition() {
        return eveningCondition;
    }

    public String getNightCondition() {
        return nightCondition;
    }
}
