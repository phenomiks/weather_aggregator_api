package ru.geekbrains.api.auth_api.model.request;

public class UserRegParams {
    private final String login;
    private final String email;
    private final char[] password;

    public UserRegParams(String login, String email, char[] password) {
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public char[] getPassword() {
        return password;
    }
}
