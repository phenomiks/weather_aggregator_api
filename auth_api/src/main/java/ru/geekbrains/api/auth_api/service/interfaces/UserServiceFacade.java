package ru.geekbrains.api.auth_api.service.interfaces;

import com.fasterxml.jackson.databind.node.ObjectNode;
import ru.geekbrains.api.auth_api.model.request.UserRegParams;

public interface UserServiceFacade {
    ObjectNode generateKeyResponse(UserRegParams regParams);
}
