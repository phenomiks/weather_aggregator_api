package ru.geekbrains.api.auth_api.model.request;

import ru.geekbrains.api.auth_api.utils.validate.EmailCustom;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegParams extends UserParams {
    @NotBlank(message = "The email must not be empty")
    @NotNull(message = "The email must not be null")
    @Size(max = 40,
            message = "The login '${validatedValue}' must not exceed {max} characters long")
    @EmailCustom(message = "The email '${validatedValue}' is not valid")
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
