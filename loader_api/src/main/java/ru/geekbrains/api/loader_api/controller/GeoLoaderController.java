package ru.geekbrains.api.loader_api.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.api.loader_api.exception.CityNotFoundException;
import ru.geekbrains.api.loader_api.exception.ErrorCodes;
import ru.geekbrains.api.loader_api.exception.LoaderApiException;
import ru.geekbrains.api.loader_api.service.GeocodingLoaderServiceImpl;
import ru.geekbrains.api.loader_api.utils.JsonResponseGenerator;

@RestController
@RequestMapping("/api/v1/loader/geo")
public class GeoLoaderController {
    private final GeocodingLoaderServiceImpl geocodingLoader;

    @Autowired
    public GeoLoaderController(GeocodingLoaderServiceImpl geocodingLoader) {
        this.geocodingLoader = geocodingLoader;
    }

    @ApiOperation(
            value = "Get city coordinate",
            notes = "{\n" +
                    "    \"city\": \"cityName\"\n" +
                    "}"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "{\n" +
                            "\"name\": " + "\"cityName\",\n" +
                            "\"lon\": \"lonCoordinate\", \n" +
                            "\"lat\": \"latCoordinate\" \n" +
                            "}"
                    )
            })
    @PostMapping("/get-coordinate")
    public ResponseEntity<Object> getCityCoordinate(@RequestBody ObjectNode city) throws CityNotFoundException {
        try {
            Object response = geocodingLoader.getResponse(city);
            return ResponseEntity.ok(response);
        } catch (CityNotFoundException e) {
            ObjectNode body = JsonResponseGenerator.generateErrorResponseJson(ErrorCodes.CITY_NOT_FOUND, e.getMessage());
            throw new LoaderApiException("Error from the OpenWeather service", body);
        }
    }
}
