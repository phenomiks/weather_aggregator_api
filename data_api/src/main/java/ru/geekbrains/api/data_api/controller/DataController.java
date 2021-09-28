package ru.geekbrains.api.data_api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.api.data_api.application.utils.JsonResponseGenerator;
import ru.geekbrains.api.data_api.application.utils.ValidateRequestUtils;
import ru.geekbrains.api.data_api.request.DataParameters;


@RestController
@RequestMapping(value = "/api/v1/data")
public class DataController {
    private final ValidateRequestUtils validateRequestUtils;

    @Autowired
    public DataController(ValidateRequestUtils validateRequestUtils) {
        this.validateRequestUtils = validateRequestUtils;
    }

    @PostMapping(value = "/get-weather")
    public ResponseEntity<?> cityData(@RequestBody ObjectNode json) throws JsonProcessingException {
        DataParameters dataParameters = validateRequestUtils.validateUserRegistrationParameters(json);

        ObjectNode response = JsonResponseGenerator.generateSuccessResponseJson();

        return ResponseEntity.ok(response);
    }
}
