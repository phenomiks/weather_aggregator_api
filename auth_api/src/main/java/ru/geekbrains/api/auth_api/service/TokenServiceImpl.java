package ru.geekbrains.api.auth_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.api.auth_api.model.Token;
import ru.geekbrains.api.auth_api.model.User;
import ru.geekbrains.api.auth_api.model.request.UserRegParams;
import ru.geekbrains.api.auth_api.repository.TokenRepository;
import ru.geekbrains.api.auth_api.service.interfaces.TokenService;
import ru.geekbrains.api.auth_api.utils.GenericBuilder;

import java.util.Set;

@Service
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;

    @Autowired
    public TokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Set<Token> findUserTokens(Long userId) {
        return tokenRepository.findAllByUserId(userId);
    }

    @Override
    public Token saveToken(User user, String tokenValue) {
        Token token = GenericBuilder.of(Token::new)
                .with(Token::setUser, user)
                .with(Token::setTokenValue, tokenValue)
                .build();

//        Token token = new Token(user, tokenValue);

        return tokenRepository.save(token);
    }
}
