package ru.geekbrains.api.data_api.model.response;

import java.io.Serializable;

public class ReportResponse extends Response implements Serializable {
    private static final long serialVersionUID = 7256832911854937703L;

    public static final String REPORT_STATUS = "report";
    private transient Object report;

    public ReportResponse(Object report) {
        super(REPORT_STATUS);
        this.report = report;
    }

    public Object getReport() {
        return report;
    }

    public void setReport(Object report) {
        this.report = report;
    }
}
