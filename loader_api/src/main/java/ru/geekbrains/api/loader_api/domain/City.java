package ru.geekbrains.api.loader_api.domain;

public class City {
    private String name;
    private Double lon;
    private Double lat;

    public City() {
    }

    public City(String name, Double lon, Double lat) {
        this.name = name;
        this.lon = lon;
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
