package ru.geekbrains.api.auth_api.model.response;

public class ReportResponse extends Response {
    public static final String REPORT_STATUS = "report";
    private Object report;

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
