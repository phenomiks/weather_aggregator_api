package ru.geekbrains.api.data_api.request;

import java.util.ArrayList;

public class DataParameters {
    private final ArrayList<String> city;
    private final ArrayList<String> services;

    public DataParameters(ArrayList<String> city, ArrayList<String> services) {
        this.city = city;
        this.services = services;
    }

    public ArrayList<String> getCity() {
        return city;
    }

    public ArrayList getServices() {
        return services;
    }
}
