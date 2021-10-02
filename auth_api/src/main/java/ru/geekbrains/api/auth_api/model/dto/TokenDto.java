package ru.geekbrains.api.auth_api.model.dto;

import ru.geekbrains.api.auth_api.model.Token;

public class TokenDto {
    private String key;

    public TokenDto(Token token) {
        this.key = token.getToken();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
