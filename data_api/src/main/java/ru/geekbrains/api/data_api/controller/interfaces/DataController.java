package ru.geekbrains.api.data_api.controller.interfaces;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/api/v1/data")
public interface DataController {
    @ApiOperation(
            value = "Getting the weather",
            notes = "Getting weather from multiple sources"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 409, message = "JSON_ERROR\n"
                    + "JSON_VALIDATION_ERROR\n"
                    + "CITY_NOT_FOUND\n"
                    + "INTERNAL_ERROR\n"
                    + "CONNECTION_REFUSED")
    })
    @PostMapping(value = "/get-weather")
    ResponseEntity<?> getWeather(@RequestBody ObjectNode json);
}

