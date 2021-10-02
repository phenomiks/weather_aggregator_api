package ru.geekbrains.api.auth_api.model.dto;

import ru.geekbrains.api.auth_api.model.Token;

import java.io.Serializable;

public class TokenDto implements Serializable {
    private static final long serialVersionUID = -4912517095299765463L;

    private String key;

    public TokenDto(Token token) {
        this.key = token.getTokenValue();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
