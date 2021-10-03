package ru.geekbrains.api.data_api.request;

import java.util.ArrayList;

public class DataParameters {
    private final String city;
    private final ArrayList<String> services;

    public DataParameters(String city, ArrayList<String> services) {
        this.city = city;
        this.services = services;
    }

    public String getCity() {
        return city;
    }

    public ArrayList<String> getServices() {
        return services;
    }
}
