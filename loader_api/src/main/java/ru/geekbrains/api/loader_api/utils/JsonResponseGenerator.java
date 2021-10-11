package ru.geekbrains.api.loader_api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ru.geekbrains.api.loader_api.domain.WeatherService;
import ru.geekbrains.api.loader_api.exception.ErrorCodes;


public class JsonResponseGenerator {
    public static ObjectNode generateSuccessResponseJson() {
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode success = mapper.createObjectNode();
        success.put("status", "success");

        return success;
    }

    public static ObjectNode generateErrorResponseJson(ErrorCodes errorCode, String replaceText) {
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode report = mapper.createObjectNode();
        String error_code =  errorCode.toString();
        String message = errorCode.replaceAndGetMessage(replaceText);
        report.put("error_code", error_code);
        report.put("message", message);

        ObjectNode error = mapper.createObjectNode();
        error.put("status", "error");
        error.set("error", report);

        return error;
    }

    public static ObjectNode generateReportResponseJson(ObjectNode report, WeatherService weatherService) {
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode successReport = mapper.createObjectNode();
        successReport.put("status", "report");

        if(weatherService == null) {
            throw new IllegalArgumentException("Weather service cannot be null");
        }

        ObjectNode reportService = mapper.createObjectNode();
        reportService.set(weatherService.getName(), report);

        successReport.set("report", reportService);

        successReport.set(weatherService.getName(), report);

        return successReport;
    }
}
