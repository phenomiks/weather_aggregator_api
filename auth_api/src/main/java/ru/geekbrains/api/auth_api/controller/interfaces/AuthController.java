package ru.geekbrains.api.auth_api.controller.interfaces;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.api.auth_api.model.request.UserParams;
import ru.geekbrains.api.auth_api.model.request.UserRegParams;
import ru.geekbrains.api.auth_api.model.response.Response;

@RequestMapping(value = "/api/v1/auth")
public interface AuthController {
    @ApiOperation(
            value = "New User Registration",
            notes = "Registration of a new user with the issuance of a personal key"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 409, message = "JSON_ERROR\n"
                            + "JSON_VALIDATION_ERROR\n"
                            + "USER_ALREADY_REGISTERED_ERROR")
            })
    @PostMapping(value = "/register")
    ResponseEntity<Response> registerUser(@RequestBody UserRegParams userRegParams);

    @ApiOperation(
            value = "Additional key generation",
            notes = "Generate additional key to registered user"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 409, message = "JSON_ERROR\n"
                    + "JSON_VALIDATION_ERROR\n"
                    + "USER_NOT_FOUND\n"
                    + "PASSWORD_NOT_CORRECT")
    })
    @PostMapping(value = "/new-key")
    ResponseEntity<Response> getNewKey(@RequestBody UserParams userParams);

    @ApiOperation(
            value = "List of all user keys ",
            notes = "Provide the user with a list of all keys issued to him"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 409, message = "JSON_ERROR\n"
                    + "JSON_VALIDATION_ERROR\n"
                    + "USER_NOT_FOUND\n"
                    + "PASSWORD_NOT_CORRECT")
    })
    @PostMapping(value = "/user-keys")
    ResponseEntity<Response> getUserKeys(@RequestBody UserParams userParams);
}
