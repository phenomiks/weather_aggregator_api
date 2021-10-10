package ru.geekbrains.api.auth_api.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.api.auth_api.model.request.UserParams;
import ru.geekbrains.api.auth_api.utils.ValidateRequestUtils;
import ru.geekbrains.api.auth_api.controller.interfaces.AuthController;
import ru.geekbrains.api.auth_api.model.request.UserRegParams;
import ru.geekbrains.api.auth_api.model.response.Response;
import ru.geekbrains.api.auth_api.service.UserTokenService;

@RestController
public class AuthControllerImpl implements AuthController {
    private final UserTokenService userTokenService;
    private final ValidateRequestUtils validateRequestUtils;

    @Autowired
    public AuthControllerImpl(UserTokenService userTokenService, ValidateRequestUtils validateRequestUtils) {
        this.userTokenService = userTokenService;
        this.validateRequestUtils = validateRequestUtils;
    }

    @Override
    public ResponseEntity<Response> registerUser(ObjectNode json) {
        UserRegParams userRegParams = validateRequestUtils.validateUserRegistrationParameters(json);

        Response response = userTokenService.generateKeyResponse(userRegParams);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Response> getNewKey(ObjectNode json) {
        UserParams userParams = validateRequestUtils.validateUserParameters(json);

        Response response = userTokenService.generateNewKeyResponse(userParams);

        return ResponseEntity.ok(response);
    }
}
