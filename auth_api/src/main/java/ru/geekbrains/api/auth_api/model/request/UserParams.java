package ru.geekbrains.api.auth_api.model.request;

public class UserParams {
    private final String login;
    private final char[] password;

    public UserParams(String login, char[] password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public char[] getPassword() {
        return password;
    }
}
