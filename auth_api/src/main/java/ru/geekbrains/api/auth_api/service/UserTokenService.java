package ru.geekbrains.api.auth_api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.api.auth_api.exception.ErrorCode;
import ru.geekbrains.api.auth_api.model.dto.UserKeysDto;
import ru.geekbrains.api.auth_api.model.request.UserParams;
import ru.geekbrains.api.auth_api.model.response.ErrorResponse;
import ru.geekbrains.api.auth_api.utils.JwtTokenUtil;
import ru.geekbrains.api.auth_api.model.Token;
import ru.geekbrains.api.auth_api.model.User;
import ru.geekbrains.api.auth_api.model.dto.TokenDto;
import ru.geekbrains.api.auth_api.model.request.UserRegParams;
import ru.geekbrains.api.auth_api.model.response.ReportResponse;
import ru.geekbrains.api.auth_api.model.response.Response;
import ru.geekbrains.api.auth_api.service.interfaces.UserServiceFacade;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

@Service
public class UserTokenService implements UserServiceFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserTokenService.class);

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
        Optional<User> optionalUser = userService.findByLoginOrEmail(regParams.getLogin(), regParams.getEmail());
        if (optionalUser.isPresent()) {
            LOGGER.warn("{}. User with login {} or email {} is already registered",
                    ErrorCode.USER_ALREADY_REGISTERED_ERROR, regParams.getLogin(), regParams.getEmail());

            return new ErrorResponse(ErrorCode.USER_ALREADY_REGISTERED_ERROR, "");
        }

        User user = userService.saveUser(regParams.getLogin(), regParams.getEmail(), regParams.getPassword());

        TokenDto tokenDto = new TokenDto(getToken(user));

        return new ReportResponse(tokenDto);
    }

    @Override
    public Response generateNewKeyResponse(UserParams userParams) {
        Optional<User> optionalUser = userService.findByLogin(userParams.getLogin());
        if (optionalUser.isEmpty()) {
            LOGGER.warn("{}. User with login {} not found", ErrorCode.USER_NOT_FOUND, userParams.getLogin());

            return new ErrorResponse(ErrorCode.USER_NOT_FOUND, userParams.getLogin());
        }
        User user = optionalUser.get();

        boolean isCorrectPassword = checkPassword(user, userParams.getPassword());
        if (!isCorrectPassword) {
            LOGGER.warn("{}. Password for user with login {} not correct",
                    ErrorCode.PASSWORD_NOT_CORRECT, user.getLogin());

            return new ErrorResponse(ErrorCode.PASSWORD_NOT_CORRECT, user.getLogin());
        }

        TokenDto tokenDto = new TokenDto(getToken(user));

        return new ReportResponse(tokenDto);
    }

    @Override
    public Response generateUserKeysResponse(UserParams userParams) {
        Optional<User> optionalUser = userService.findByLogin(userParams.getLogin());
        if (optionalUser.isEmpty()) {
            LOGGER.warn("{}. User with login {} not found", ErrorCode.USER_NOT_FOUND, userParams.getLogin());

            return new ErrorResponse(ErrorCode.USER_NOT_FOUND, userParams.getLogin());
        }
        User user = optionalUser.get();

        boolean isCorrectPassword = checkPassword(user, userParams.getPassword());
        if (!isCorrectPassword) {
            LOGGER.warn("{}. Password for user with login {} not correct",
                    ErrorCode.PASSWORD_NOT_CORRECT, user.getLogin());

            return new ErrorResponse(ErrorCode.PASSWORD_NOT_CORRECT, user.getLogin());
        }

        Set<Token> tokens = tokenService.findUserTokens(user.getId());
        UserKeysDto userKeys = new UserKeysDto(tokens);

        return new ReportResponse(userKeys);
    }

    private Token getToken(User user) {
        String key = jwtTokenUtil.generateToken(user.getLogin());

        return tokenService.saveToken(user, key);
    }

    private boolean checkPassword(User user, char[] requestPassword) {
        return Arrays.equals(requestPassword, user.getPassword());
    }
}
