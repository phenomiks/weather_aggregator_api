package ru.geekbrains.front.entity;

import java.util.Arrays;

public class WeatherRequestForm {
    private String city;
    private String[] services;
    private boolean needDetailed;
    private String key;

    public WeatherRequestForm(String city, String[] services, boolean needDetailed, String key) {
        this.city = city;
        this.services = services;
        this.needDetailed = needDetailed;
        this.key = key;
    }

    public String getCity() {
        return city;
    }

    public String[] getServices() {
        return services;
    }

    public boolean isNeedDetailed() {
        return needDetailed;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "WeatherRequest{" +
                "city='" + city + '\'' +
                ", services=" + Arrays.toString(services) +
                ", needDetailed=" + needDetailed +
                ", key='" + key + '\'' +
                '}';
    }
}
