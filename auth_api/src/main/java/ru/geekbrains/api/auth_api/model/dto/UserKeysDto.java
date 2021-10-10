package ru.geekbrains.api.auth_api.model.dto;

import ru.geekbrains.api.auth_api.model.Token;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class UserKeysDto  implements Serializable {
    private static final long serialVersionUID = -4483037050710264463L;

    private Set<String> keys;

    public UserKeysDto(Collection<Token> tokens) {
        this.keys = (Objects.isNull(tokens)) ? null : convertToTokenValuesCollection(tokens);
    }

    private Set<String> convertToTokenValuesCollection(Collection<Token> tokens) {
        return tokens.stream()
                .filter(Objects::nonNull)
                .map(Token::getTokenValue)
                .collect(Collectors.toSet());
    }

    public Set<String> getKeys() {
        return keys;
    }

    public void setKeys(Set<String> keys) {
        this.keys = keys;
    }
}
