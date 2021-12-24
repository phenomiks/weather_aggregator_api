package ru.geekbrains.api.loader_api.utils;

import ru.geekbrains.api.loader_api.domain.City;

public class ValidatorResponseUtils {

    public static void validateCity(City city) {
        if (city.getLat() == null || city.getLon() == null) {
            throw new IllegalArgumentException("City should have latitude and longitude");
        }
    }

}
