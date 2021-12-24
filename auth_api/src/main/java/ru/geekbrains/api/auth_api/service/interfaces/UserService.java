package ru.geekbrains.api.auth_api.service.interfaces;

import ru.geekbrains.api.auth_api.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByLogin(String login);

    Optional<User> findByLoginOrEmail(String login, String email);

    User saveUser(String login, String email, char[] password);
}
