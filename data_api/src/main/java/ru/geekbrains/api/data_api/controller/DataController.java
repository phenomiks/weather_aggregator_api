package ru.geekbrains.api.data_api.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/api/v1/data")
public interface DataController {
    @PostMapping(value = "/get-weather")
    ResponseEntity<ObjectNode> cityData(@RequestBody ObjectNode json);
}

