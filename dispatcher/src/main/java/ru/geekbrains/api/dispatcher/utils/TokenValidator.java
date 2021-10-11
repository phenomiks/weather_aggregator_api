package ru.geekbrains.api.dispatcher.utils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.stereotype.Component;

@Component
public class TokenValidator {

    private final JwtUtils jwtUtils;

    public TokenValidator(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    public void checkException(String key) {
        try {
            jwtUtils.getUsernameFromToken(key);
        } catch (MalformedJwtException | IllegalArgumentException e) {
            ExceptionHandlerForToken.keyNotValidException();
        } catch (ExpiredJwtException e) {
            ExceptionHandlerForToken.keyExpiredException();
        }
    }
}
