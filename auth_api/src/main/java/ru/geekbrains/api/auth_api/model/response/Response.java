package ru.geekbrains.api.auth_api.model.response;

public abstract class Response {
    private String status;

    public Response(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
