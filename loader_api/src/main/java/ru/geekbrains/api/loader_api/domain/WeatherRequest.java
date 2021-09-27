package ru.geekbrains.api.loader_api.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class WeatherRequest {
    @NotNull(message = "City must not be null")
    private final String city;
    @NotEmpty(message = "WeatherServices must not be null")
    private final Set<WeatherService> weatherServices;

    public WeatherRequest(@NotNull(message = "City must not be null") String city,
                          @NotEmpty(message = "WeatherServices must not be null") Set<WeatherService> weatherServices) {
        this.city = city;
        this.weatherServices = weatherServices;
    }

    public String getCity() {
        return city;
    }

    public Set<WeatherService> getWeatherServices() {
        return weatherServices;
    }
}
