package ru.geekbrains.api.auth_api.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.api.auth_api.application.utils.JsonResponseGenerator;
import ru.geekbrains.api.auth_api.application.utils.ValidateRequestUtils;
import ru.geekbrains.api.auth_api.model.request.UserRegParams;

@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthController {
    private final ValidateRequestUtils validateRequestUtils;

    @Autowired
    public AuthController(ValidateRequestUtils validateRequestUtils) {
        this.validateRequestUtils = validateRequestUtils;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> registerUser(@RequestBody ObjectNode json) {
        UserRegParams userRegParams = validateRequestUtils.validateUserRegistrationParameters(json);

        ObjectNode response = JsonResponseGenerator.generateSuccessResponseJson();

        return ResponseEntity.ok(response);
    }
}
