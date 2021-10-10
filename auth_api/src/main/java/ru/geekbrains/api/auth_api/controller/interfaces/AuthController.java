package ru.geekbrains.api.auth_api.controller.interfaces;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.api.auth_api.model.response.Response;

@RequestMapping(value = "/api/v1/auth")
public interface AuthController {
    @PostMapping(value = "/register")
    ResponseEntity<Response> registerUser(@RequestBody ObjectNode json);

    @PostMapping(value = "/new-key")
    ResponseEntity<Response> getNewKey(@RequestBody ObjectNode json);
}
