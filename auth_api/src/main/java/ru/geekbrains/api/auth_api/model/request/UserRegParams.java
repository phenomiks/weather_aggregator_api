package ru.geekbrains.api.auth_api.model.request;

public class UserRegParams extends UserParams {
    private String email;

    public UserRegParams() {
    }

    public UserRegParams(String login, char[] password, String email) {
        super(login, password);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
