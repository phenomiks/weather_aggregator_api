package ru.geekbrains.api.data_api.model.request;

import java.util.ArrayList;

public class DataParameters {
    private final String city;
    private final ArrayList<String> services;
    private final boolean needDetailed;

    public DataParameters(String city, ArrayList<String> services, boolean needDetailed) {
        this.city = city;
        this.services = services;
        this.needDetailed = needDetailed;
    }

    public String getCity() { return city; }

    public ArrayList<String> getServices() { return services; }

    public boolean needDetailed() { return needDetailed; }
}
