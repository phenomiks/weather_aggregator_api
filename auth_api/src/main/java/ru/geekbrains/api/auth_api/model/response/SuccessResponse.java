package ru.geekbrains.api.auth_api.model.response;

public class SuccessResponse extends Response {
    public static final String SUCCESS_STATUS = "success";

    public SuccessResponse() {
        super(SUCCESS_STATUS);
    }
}
