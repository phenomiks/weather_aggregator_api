package ru.geekbrains.api.auth_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.api.auth_api.application.utils.JwtTokenUtil;
import ru.geekbrains.api.auth_api.model.Token;
import ru.geekbrains.api.auth_api.model.User;
import ru.geekbrains.api.auth_api.model.dto.TokenDto;
import ru.geekbrains.api.auth_api.model.request.UserRegParams;
import ru.geekbrains.api.auth_api.model.response.ReportResponse;
import ru.geekbrains.api.auth_api.model.response.Response;
import ru.geekbrains.api.auth_api.service.interfaces.UserServiceFacade;

import java.util.Optional;

@Service
public class UserTokenService implements UserServiceFacade {
    private final UserServiceImpl userService;
    private final TokenServiceImpl tokenService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public UserTokenService(UserServiceImpl userService, TokenServiceImpl tokenService,
                            JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public Response generateKeyResponse(UserRegParams regParams) {
        Optional<User> optionalUser = userService.findByLogin(regParams.getLogin());
        User user = (optionalUser.isEmpty())
                ? userService.saveUser(regParams.getLogin(), regParams.getEmail(), regParams.getPassword())
                : optionalUser.get();

        String key = jwtTokenUtil.generateToken(user.getLogin());

        Token token = tokenService.saveToken(user, key);
        TokenDto tokenDto = new TokenDto(token);

        return new ReportResponse(tokenDto);
    }
}
