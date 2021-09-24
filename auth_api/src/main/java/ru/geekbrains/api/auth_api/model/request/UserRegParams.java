package ru.geekbrains.api.auth_api.model.request;

public class UserRegParams {
    private final String login;
    private final String email;
    private final String password;

    public UserRegParams(String login, String email, String password) {
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

    public String getPassword() {
        return password;
    }
}
