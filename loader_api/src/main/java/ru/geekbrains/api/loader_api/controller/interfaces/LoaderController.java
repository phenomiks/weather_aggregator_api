package ru.geekbrains.api.loader_api.controller.interfaces;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/loader")
public interface LoaderController {

    @ApiOperation(
            value = "Get weather by city",
            notes = "{\n" +
                    "    \"city\": \"cityName\",\n" +
                    "    \"services\": [\"openweather\", \"yandexweather\"]\n" +
                    "}"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "\"status\": " + "\"report\",\n" +
                            "\"report\": {\n" +
                            "\"yandexweather\": { objectNode }, \n" +
                            "\"openweather\": { objectNode }, \n" +
                            "}"
                    )
            })
    @PostMapping("/get-weather")
    ResponseEntity<?> getWeatherByCity(@RequestBody ObjectNode objectNode);
}
