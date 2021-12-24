package ru.geekbrains.api.dispatcher.controller.interfaces;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/api/v1/dispatcher")
public interface DispatcherController {

    @ApiOperation(
            value = "New User Registration",
            notes = "Transferring data to Auth_Api module for registration of a new user with " +
                    "the issuance of a personal key"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 409, message = "JSON_ERROR\n"
                            + "JSON_VALIDATION_ERROR\n"
                            + "USER_ALREADY_REGISTERED_ERROR\n"
                            + "INTERNAL_ERROR\n"
                            + "CONNECTION_REFUSED")
            })
    @PostMapping(value = "/register")
    ResponseEntity<?> registerUser(@RequestBody ObjectNode json);

    @ApiOperation(
            value = "Additional key generation",
            notes = "Transferring data to Auth_Api module for generate additional key to " +
                    "registered user"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 409, message = "JSON_ERROR\n"
                    + "JSON_VALIDATION_ERROR\n"
                    + "USER_NOT_FOUND\n"
                    + "PASSWORD_NOT_CORRECT\n"
                    + "INTERNAL_ERROR\n"
                    + "CONNECTION_REFUSED")
    })
    @PostMapping(value = "/new-key")
    ResponseEntity<?> getNewKey(@RequestBody ObjectNode json);

    @ApiOperation(
            value = "List of all user keys",
            notes = "Transferring data to Auth_Api module for provide the user with a list of " +
                    "all keys issued to him"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 409, message = "JSON_ERROR\n"
                    + "JSON_VALIDATION_ERROR\n"
                    + "USER_NOT_FOUND\n"
                    + "PASSWORD_NOT_CORRECT\n"
                    + "INTERNAL_ERROR\n"
                    + "CONNECTION_REFUSED")
    })
    @PostMapping(value = "/user-keys")
    ResponseEntity<?> getUserKeys(@RequestBody ObjectNode json);

    @ApiOperation(
            value = "Getting weather",
            notes = "Transferring data to Data_Api module for getting weather (short / full)"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 409, message = "JSON_ERROR\n"
                            + "JSON_VALIDATION_ERROR\n"
                            + "INTERNAL_ERROR\n"
                            + "CONNECTION_REFUSED\n"
                            + "KEY_EXPIRED\n"
                            + "KEY_NOT_VALID")
            })
    @PostMapping(value = "/get-weather")
    ResponseEntity<?> getWeather(@RequestBody ObjectNode json);
}
