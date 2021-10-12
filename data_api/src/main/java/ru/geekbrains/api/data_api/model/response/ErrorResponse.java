package ru.geekbrains.api.data_api.model.response;



import ru.geekbrains.api.data_api.application.exception.ErrorCode;

import java.io.Serializable;

public class ErrorResponse extends Response implements Serializable {
    private static final long serialVersionUID = 8408220217852964256L;

    public static final String ERROR_STATUS = "error";
    private transient Error error;

    public ErrorResponse(ErrorCode errorCode, String replaceText) {
        super(ERROR_STATUS);
        this.error = new Error(errorCode, errorCode.replaceAndGetMessage(replaceText));
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
