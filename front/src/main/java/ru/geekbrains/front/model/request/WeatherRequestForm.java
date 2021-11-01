package ru.geekbrains.front.model.request;

import java.util.Set;

public class WeatherRequestForm {
    private final String city;
    private final Set<String> services;
    private final boolean needDetailed;
    private final String key;

    public WeatherRequestForm(String city, Set<String> services, boolean needDetailed, String key) {
        this.city = city;
        this.services = services;
        this.needDetailed = needDetailed;
        this.key = key;
    }

    public String getCity() {
        return city;
    }

    public Set<String> getServices() {
        return services;
    }

    public boolean isNeedDetailed() {
        return needDetailed;
    }

    public String getKey() {
        return key;
    }
}
