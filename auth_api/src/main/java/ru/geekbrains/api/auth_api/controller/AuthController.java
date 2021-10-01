package ru.geekbrains.api.auth_api.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/api/v1/auth")
public interface AuthController {
    @PostMapping(value = "/register")
    ResponseEntity<?> registerUser(@RequestBody ObjectNode json);
}
