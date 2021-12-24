package ru.geekbrains.api.auth_api.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserParams {
    @NotBlank(message = "The login must not be empty")
    @NotNull(message = "The login must not be null")
    @Size(min = 4,
            max = 30,
            message = "The login '${validatedValue}' must be between {min} and {max} characters long")
    private String login;

    @NotEmpty(message = "The password must not be empty")
    @NotNull(message = "The password must not be null")
    @Size(min = 4,
            max = 64,
            message = "The password must be between {min} and {max} characters long")
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
