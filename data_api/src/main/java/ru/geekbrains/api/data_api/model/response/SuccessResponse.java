package ru.geekbrains.api.data_api.model.response;

import java.io.Serializable;

public class SuccessResponse extends Response implements Serializable {
    private static final long serialVersionUID = 4029985365514293281L;

    public static final String SUCCESS_STATUS = "success";

    public SuccessResponse() {
        super(SUCCESS_STATUS);
    }
}
