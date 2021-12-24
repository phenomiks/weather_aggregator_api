package ru.geekbrains.front.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ru.geekbrains.front.model.TimesOfDay;
import ru.geekbrains.front.model.WeatherService;
import ru.geekbrains.front.model.response.CurrentWeather;
import ru.geekbrains.front.model.response.DayWeather;
import ru.geekbrains.front.model.response.Weather;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.Map;

public final class OpenWeatherUtils {
    private static final double ONE_PA_IN_MM_HG = 0.0075006156130264;
    private OpenWeatherUtils() {
    }

    public static Weather fillWeatherEntity(ObjectNode weatherJson) {
        JsonNode jsonNode = weatherJson.get("report").get(WeatherService.OPEN_WEATHER.getName());
        CurrentWeather currentWeather = getCurrentWeather(jsonNode.get("current"));

        JsonNode dayNode = jsonNode.get("daily").get(0);
        DayWeather morningWeather = getDayWeather(dayNode, "morn");
        DayWeather dayWeather = getDayWeather(dayNode, "day");
        DayWeather eveningWeather = getDayWeather(dayNode, "eve");
        DayWeather nightWeather = getDayWeather(dayNode, "night");

        Map<String, DayWeather> dayWeatherMap = new LinkedHashMap<>();
        dayWeatherMap.put(TimesOfDay.MORNING.getAlias(), morningWeather);
        dayWeatherMap.put(TimesOfDay.DAY.getAlias(), dayWeather);
        dayWeatherMap.put(TimesOfDay.EVENING.getAlias(), eveningWeather);
        dayWeatherMap.put(TimesOfDay.NIGHT.getAlias(), nightWeather);

        return new Weather(currentWeather, dayWeatherMap);
    }

    private static CurrentWeather getCurrentWeather(JsonNode json) {
        double temperature = json.get("temp").asDouble();
        int roundTemp = getRoundUpValue(temperature);

        double windSpeed = json.get("wind_speed").asDouble();
        int humidity = json.get("humidity").asInt();
        int pressure = json.get("pressure").asInt();
        int pressureInMmHg = convertPressureFromhPaTommHg(pressure);

        String condition = json.get("weather").get(0).get("description").asText();

        return new CurrentWeather(roundTemp, windSpeed, humidity, pressureInMmHg, condition);
    }

    private static DayWeather getDayWeather(JsonNode json, String timesOfDay) {
        double temperature = json.get("temp").get(timesOfDay).asDouble();
        int roundTemp = getRoundUpValue(temperature);

        double windSpeed = json.get("wind_speed").asDouble();
        int humidity = json.get("humidity").asInt();
        int pressure = json.get("pressure").asInt();
        int pressureInMmHg = convertPressureFromhPaTommHg(pressure);

        String condition = json.get("weather").get(0).get("description").asText();

        double feelTemperature = json.get("feels_like").get(timesOfDay).asDouble();
        int roundFeelTemp = getRoundUpValue(feelTemperature);

        return new DayWeather(roundTemp, windSpeed, humidity, pressureInMmHg, condition, roundFeelTemp);
    }

    private static int getRoundUpValue(double value) {
        return BigDecimal.valueOf(value)
                .setScale(0, RoundingMode.HALF_UP)
                .intValue();
    }

    private static int convertPressureFromhPaTommHg(int pressureInhPa) {
        return getRoundUpValue(pressureInhPa * 100 * ONE_PA_IN_MM_HG);
    }
}
