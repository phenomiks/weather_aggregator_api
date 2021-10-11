package ru.geekbrains.api.auth_api.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.api.auth_api.model.request.UserParams;
import ru.geekbrains.api.auth_api.model.response.ErrorResponse;
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
        Pair<ErrorResponse, UserRegParams> pair = validateRequestUtils.validateUserRegistrationParameters(json);
        if (pair.getLeft() != null) {
            return getErrorResponse(pair.getLeft());
        }

        Response response = userTokenService.generateKeyResponse(pair.getRight());
        if (response.getStatus().equals(ErrorResponse.ERROR_STATUS)) {
            return getErrorResponse(response);
        }

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Response> getNewKey(ObjectNode json) {
        Pair<ErrorResponse, UserParams> pair = validateRequestUtils.validateUserParameters(json);
        if (pair.getLeft() != null) {
            return getErrorResponse(pair.getLeft());
        }

        Response response = userTokenService.generateNewKeyResponse(pair.getRight());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Response> getUserKeys(ObjectNode json) {
        Pair<ErrorResponse, UserParams> pair = validateRequestUtils.validateUserParameters(json);
        if (pair.getLeft() != null) {
            return getErrorResponse(pair.getLeft());
        }

        Response response = userTokenService.generateUserKeysResponse(pair.getRight());

        return ResponseEntity.ok(response);
    }

    private ResponseEntity<Response> getErrorResponse(Response response) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }
}
