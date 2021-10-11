package ru.geekbrains.api.data_api.model.response;

import java.io.Serializable;

public abstract class Response implements Serializable {
    private static final long serialVersionUID = -699363788573225795L;

    private final String status;

    protected Response(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
