package ru.geekbrains.api.auth_api.service.interfaces;

import ru.geekbrains.api.auth_api.model.request.UserParams;
import ru.geekbrains.api.auth_api.model.request.UserRegParams;
import ru.geekbrains.api.auth_api.model.response.Response;

public interface UserServiceFacade {
    Response generateKeyResponse(UserRegParams regParams);

    Response generateNewKeyResponse(UserParams regParams);

    Response generateUserKeysResponse(UserParams userParams);
}
