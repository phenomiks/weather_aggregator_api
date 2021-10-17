package ru.geekbrains.api.auth_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.api.auth_api.model.request.UserParams;
import ru.geekbrains.api.auth_api.model.response.ErrorResponse;
import ru.geekbrains.api.auth_api.service.interfaces.UserServiceFacade;
import ru.geekbrains.api.auth_api.controller.interfaces.AuthController;
import ru.geekbrains.api.auth_api.model.request.UserRegParams;
import ru.geekbrains.api.auth_api.model.response.Response;

import javax.validation.Valid;

@RestController
public class AuthControllerImpl implements AuthController {
    private final UserServiceFacade userTokenService;

    @Autowired
    public AuthControllerImpl(UserServiceFacade userTokenService) {
        this.userTokenService = userTokenService;
    }

    @Override
    public ResponseEntity<Response> registerUser(@Valid UserRegParams userRegParams) {
        Response response = userTokenService.generateKeyResponse(userRegParams);

        return checkAndReturnResponse(response);
    }

    @Override
    public ResponseEntity<Response> getNewKey(@Valid UserParams userParams) {
        Response response = userTokenService.generateNewKeyResponse(userParams);

        return checkAndReturnResponse(response);
    }

    @Override
    public ResponseEntity<Response> getUserKeys(@Valid UserParams userParams) {
        Response response = userTokenService.generateUserKeysResponse(userParams);

        return checkAndReturnResponse(response);
    }

    private ResponseEntity<Response> checkAndReturnResponse(Response response) {
        if (response.getStatus().equals(ErrorResponse.ERROR_STATUS)) {
            return getErrorResponse(response);
        }

        return ResponseEntity.ok(response);
    }

    private ResponseEntity<Response> getErrorResponse(Response response) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }
}
