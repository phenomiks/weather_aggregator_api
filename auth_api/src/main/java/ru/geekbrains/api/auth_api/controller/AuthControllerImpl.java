package ru.geekbrains.api.auth_api.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.api.auth_api.application.utils.JsonResponseGenerator;
import ru.geekbrains.api.auth_api.application.utils.ValidateRequestUtils;
import ru.geekbrains.api.auth_api.model.request.UserRegParams;

@RestController
public class AuthControllerImpl implements AuthController {
    private final ValidateRequestUtils validateRequestUtils;

    @Autowired
    public AuthControllerImpl(ValidateRequestUtils validateRequestUtils) {
        this.validateRequestUtils = validateRequestUtils;
    }

    @Override
    public ResponseEntity<?> registerUser(ObjectNode json) {
        UserRegParams userRegParams = validateRequestUtils.validateUserRegistrationParameters(json);

        ObjectNode response = JsonResponseGenerator.generateSuccessResponseJson();

        return ResponseEntity.ok(response);
    }
}
