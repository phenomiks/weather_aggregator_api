package ru.geekbrains.front.utils;

public class WeatherUtil {

    public static String convertTemperature(Double temperature){
        if (temperature > 0)
            return "+" + Math.round(temperature);

        return "" + Math.round(temperature);
    }

    public static String convertTemperature(int temperature){
        if (temperature > 0)
            return "+" + temperature;

        return "" + Math.round(temperature);
    }
}
