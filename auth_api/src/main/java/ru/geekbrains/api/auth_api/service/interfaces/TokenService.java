package ru.geekbrains.api.auth_api.service.interfaces;

import ru.geekbrains.api.auth_api.model.Token;
import ru.geekbrains.api.auth_api.model.User;

import java.util.Set;

public interface TokenService {
    Token saveToken(User user, String tokenValue);

    Set<Token> findUserTokens(Long userId);
}
