package ru.geekbrains.api.auth_api.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.geekbrains.api.auth_api.model.Token;
import ru.geekbrains.api.auth_api.model.User;
import ru.geekbrains.api.auth_api.repository.TokenRepository;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest(classes = TokenServiceImpl.class)
@ActiveProfiles("test")
class TokesServiceImplTests {
    @Autowired
    TokenServiceImpl tokenService;

    @MockBean
    TokenRepository tokenRepository;

    @Test
    void findUserTokensTest() {
        Long userId = 1L;

        Set<Token> tokensFromDB = new HashSet<>();
        tokensFromDB.add(new Token(null, "sva12.zx2sas12.cvx321"));
        tokensFromDB.add(new Token(null, "zz2xz2.as21zxr3.asx12kr"));

        Mockito.doReturn(tokensFromDB)
                .when(tokenRepository)
                .findAllByUserId(userId);

        Set<Token> tokens = tokenService.findUserTokens(userId);

        Mockito.verify(tokenRepository, Mockito.times(1))
                .findAllByUserId(userId);

        Assertions.assertNotNull(tokens);
        Assertions.assertEquals(tokensFromDB, tokens);
    }

    @Test
    void saveTokenTest() {
        User userFromDB = new User();
        userFromDB.setId(5L);
        userFromDB.setLogin("testUser");
        userFromDB.setEmail("testUser@gmail.com");
        userFromDB.setPassword("$2y$12$tB5v".toCharArray());

        Token tokenFromDB = new Token();
        tokenFromDB.setId(7L);
        tokenFromDB.setUser(userFromDB);
        tokenFromDB.setTokenValue("asf2wfs.asd53we2fs.asd32r2fs2dv");

        Mockito.doReturn(tokenFromDB)
                .when(tokenRepository)
                .save(Mockito.any(Token.class));

        Token token = tokenService.saveToken(userFromDB, tokenFromDB.getTokenValue());

        Mockito.verify(tokenRepository, Mockito.times(1))
                .save(Mockito.any(Token.class));

        Assertions.assertNotNull(token);
        Assertions.assertEquals(tokenFromDB.getTokenValue(), token.getTokenValue());
    }
}
