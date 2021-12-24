package ru.geekbrains.api.auth_api.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.geekbrains.api.auth_api.exception.ErrorCode;
import ru.geekbrains.api.auth_api.model.Token;
import ru.geekbrains.api.auth_api.model.User;
import ru.geekbrains.api.auth_api.model.dto.TokenDto;
import ru.geekbrains.api.auth_api.model.dto.UserKeysDto;
import ru.geekbrains.api.auth_api.model.request.UserParams;
import ru.geekbrains.api.auth_api.model.request.UserRegParams;
import ru.geekbrains.api.auth_api.model.response.ErrorResponse;
import ru.geekbrains.api.auth_api.model.response.ReportResponse;
import ru.geekbrains.api.auth_api.model.response.Response;
import ru.geekbrains.api.auth_api.service.interfaces.TokenService;
import ru.geekbrains.api.auth_api.service.interfaces.UserService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
@ActiveProfiles("test")
class UserTokenServiceTests {
    @Autowired
    UserTokenService userTokenService;

    @MockBean
    UserService userService;

    @MockBean
    TokenService tokenService;

    User userFromDB;

    @BeforeEach
    void init() {
        userFromDB = new User();
        userFromDB.setId(1L);
        userFromDB.setLogin("test");
        userFromDB.setEmail("test@gmail.com");
        userFromDB.setPassword("123456".toCharArray());
    }

    @Test
    void userAlreadyRegisteredErrorTest() {
        Mockito.doReturn(Optional.of(userFromDB))
                .when(userService)
                .findByLoginOrEmail(Mockito.any(), Mockito.any());

        UserRegParams userRegParams = new UserRegParams(userFromDB.getLogin(),
                userFromDB.getPassword(), userFromDB.getEmail());

        Response response = userTokenService.generateKeyResponse(userRegParams);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(ErrorResponse.ERROR_STATUS, response.getStatus());

        ErrorResponse errorResponse = (ErrorResponse) response;
        Assertions.assertEquals(ErrorCode.USER_ALREADY_REGISTERED_ERROR, errorResponse.getError().getErrorCode());
    }

    @Test
    void generateKeyResponseTest() {
        Mockito.doReturn(Optional.empty())
                .when(userService)
                .findByLoginOrEmail(Mockito.any(), Mockito.any());

        UserRegParams userRegParams = new UserRegParams(userFromDB.getLogin(),
                userFromDB.getPassword(), userFromDB.getEmail());

        Mockito.doReturn(userFromDB)
                .when(userService)
                .saveUser(Mockito.any(), Mockito.any(), Mockito.any());

        Token token = new Token(null, "123");
        Mockito.doReturn(token)
                .when(tokenService)
                .saveToken(Mockito.eq(userFromDB), Mockito.any());

        Response response = userTokenService.generateKeyResponse(userRegParams);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(ReportResponse.REPORT_STATUS, response.getStatus());

        ReportResponse reportResponse = (ReportResponse) response;
        TokenDto tokenDto = (TokenDto) reportResponse.getReport();

        Assertions.assertEquals(token.getTokenValue(), tokenDto.getKey());
    }

    @Test
    void userNotFoundErrorTest() {
        Mockito.doReturn(Optional.empty())
                .when(userService)
                .findByLogin(Mockito.any());

        UserParams userParams = new UserParams(userFromDB.getLogin(), userFromDB.getPassword());

        Response response = userTokenService.generateNewKeyResponse(userParams);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(ErrorResponse.ERROR_STATUS, response.getStatus());

        ErrorResponse errorResponse = (ErrorResponse) response;
        Assertions.assertEquals(ErrorCode.USER_NOT_FOUND, errorResponse.getError().getErrorCode());
    }

    @Test
    void passwordNotCorrectErrorTest() {
        Mockito.doReturn(Optional.of(userFromDB))
                .when(userService)
                .findByLogin(Mockito.any());

        UserParams userParams = new UserParams(userFromDB.getLogin(), "notValidPassword".toCharArray());

        Response response = userTokenService.generateNewKeyResponse(userParams);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(ErrorResponse.ERROR_STATUS, response.getStatus());

        ErrorResponse errorResponse = (ErrorResponse) response;
        Assertions.assertEquals(ErrorCode.PASSWORD_NOT_CORRECT, errorResponse.getError().getErrorCode());
    }

    @Test
    void generateUserKeysResponseTest() {
        Mockito.doReturn(Optional.of(userFromDB))
                .when(userService)
                .findByLogin(Mockito.any());

        UserParams userParams = new UserParams(userFromDB.getLogin(), userFromDB.getPassword());

        Mockito.doReturn(userFromDB)
                .when(userService)
                .saveUser(Mockito.any(), Mockito.any(), Mockito.any());

        Set<Token> tokensFromDB = new HashSet<>();
        tokensFromDB.add(new Token(null, "sva12.zx2sas12.cvx321"));
        tokensFromDB.add(new Token(null, "zz2xz2.as21zxr3.asx12kr"));

        Mockito.doReturn(tokensFromDB)
                .when(tokenService)
                .findUserTokens(userFromDB.getId());

        Response response = userTokenService.generateUserKeysResponse(userParams);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(ReportResponse.REPORT_STATUS, response.getStatus());

        ReportResponse reportResponse = (ReportResponse) response;
        UserKeysDto userKeysDto = (UserKeysDto) reportResponse.getReport();

        Set<String> keys = tokensFromDB.stream()
                .map(Token::getTokenValue)
                .collect(Collectors.toSet());

        Assertions.assertEquals(keys, userKeysDto.getKeys());
    }
}
