package ru.geekbrains.front.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ru.geekbrains.front.model.TimesOfDay;
import ru.geekbrains.front.model.WeatherService;
import ru.geekbrains.front.model.response.CurrentWeather;
import ru.geekbrains.front.model.response.DayWeather;
import ru.geekbrains.front.model.response.Weather;

import java.util.LinkedHashMap;
import java.util.Map;

public final class YandexWeatherUtils {
    private YandexWeatherUtils() {
    }

    public static Weather fillWeatherEntity(ObjectNode weatherJson) {
        JsonNode jsonNode = weatherJson.get("report").get(WeatherService.YANDEX_WEATHER.getName());
        CurrentWeather currentWeather = getCurrentWeather(jsonNode.get("fact"));

        JsonNode partsDayNode = jsonNode.get("forecasts").get(0).get("parts");
        DayWeather morningWeather = getDayWeather(partsDayNode.get("morning"));
        DayWeather dayWeather = getDayWeather(partsDayNode.get("day"));
        DayWeather eveningWeather = getDayWeather(partsDayNode.get("evening"));
        DayWeather nightWeather = getDayWeather(partsDayNode.get("night"));

        Map<String, DayWeather> dayWeatherMap = new LinkedHashMap<>();
        dayWeatherMap.put(TimesOfDay.MORNING.getAlias(), morningWeather);
        dayWeatherMap.put(TimesOfDay.DAY.getAlias(), dayWeather);
        dayWeatherMap.put(TimesOfDay.EVENING.getAlias(), eveningWeather);
        dayWeatherMap.put(TimesOfDay.NIGHT.getAlias(), nightWeather);

        return new Weather(currentWeather, dayWeatherMap);
    }

    private static CurrentWeather getCurrentWeather(JsonNode json) {
        int temperature = json.get("temp").asInt();
        double windSpeed = json.get("wind_speed").asDouble();
        int humidity = json.get("humidity").asInt();
        int pressure = json.get("pressure_mm").asInt();
        String condition = json.get("condition").asText();

        return new CurrentWeather(temperature, windSpeed, humidity, pressure, condition);
    }

    private static DayWeather getDayWeather(JsonNode json) {
        int temperature = json.get("temp_avg").asInt();
        double windSpeed = json.get("wind_speed").asDouble();
        int humidity = json.get("humidity").asInt();
        int pressure = json.get("pressure_mm").asInt();
        String condition = json.get("condition").asText();
        int feelTemperature = json.get("feels_like").asInt();

        return new DayWeather(temperature, windSpeed, humidity, pressure, condition, feelTemperature);
    }
}
