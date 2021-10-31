package ru.geekbrains.api.loader_api.domain;

public class WeatherResponse {
    private String status;
    private Report report;

    public WeatherResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }
}
