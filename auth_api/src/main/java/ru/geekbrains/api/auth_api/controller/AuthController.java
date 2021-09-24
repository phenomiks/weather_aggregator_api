package ru.geekbrains.api.auth_api.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.api.auth_api.application.utils.JsonResponseGenerator;

@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthController {
    @PostMapping(value = "/register")
    public ResponseEntity<?> registerUser(@RequestBody ObjectNode json) {
        ObjectNode response = JsonResponseGenerator.generateSuccessResponseJson();

        return ResponseEntity.ok(response);
    }
}
