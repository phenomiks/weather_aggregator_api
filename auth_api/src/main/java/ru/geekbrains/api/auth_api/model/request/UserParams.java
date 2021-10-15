package ru.geekbrains.api.auth_api.model.request;

public class UserParams {
    private String login;
    private char[] password;

    public UserParams() {
    }

    public UserParams(String login, char[] password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }
}
