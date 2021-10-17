package ru.geekbrains.api.auth_api.controller.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.api.auth_api.model.request.UserParams;
import ru.geekbrains.api.auth_api.model.request.UserRegParams;
import ru.geekbrains.api.auth_api.model.response.Response;

@RequestMapping(value = "/api/v1/auth")
public interface AuthController {
    @PostMapping(value = "/register")
    ResponseEntity<Response> registerUser(@RequestBody UserRegParams userRegParams);

    @PostMapping(value = "/new-key")
    ResponseEntity<Response> getNewKey(@RequestBody UserParams userParams);

    @PostMapping(value = "/user-keys")
    ResponseEntity<Response> getUserKeys(@RequestBody UserParams userParams);
}
