package ru.geekbrains.api.dispatcher.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.geekbrains.api.dispatcher.exception.ErrorCode;


@SpringBootTest(classes = AuthService.class)
public class AuthServiceTest {

    @Autowired
    AuthService authService;

    @MockBean
    PostRequester postRequester;

    @Test
    void registerUserTimeOutExceptionTest() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();
        json.put("login", "test");
        json.put("email", "test@gmail.com");
        json.put("password", "11111");

        Mockito.doReturn(null)
                .when(postRequester)
                .doPost(Mockito.any(), Mockito.any());

        try {
            authService.registerUser(json);
            Assertions.fail("Expected RuntimeException");
        } catch (RuntimeException thrown) {
            Assertions.assertEquals("CONNECTION_REFUSED. " + ErrorCode.CONNECTION_REFUSED.getMessage(), thrown.getMessage());
        }
    }

    @Test
    void getNewKeyTimeOutExceptionTest() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();
        json.put("login", "test");
        json.put("password", "11111");

        Mockito.doReturn(null)
                .when(postRequester)
                .doPost(Mockito.any(), Mockito.any());

        try {
            authService.getNewKey(json);
            Assertions.fail("Expected RuntimeException");
        } catch (RuntimeException thrown) {
            Assertions.assertEquals("CONNECTION_REFUSED. " + ErrorCode.CONNECTION_REFUSED.getMessage(), thrown.getMessage());
        }
    }

    @Test
    void getUserKeysTimeOutExceptionTest() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();
        json.put("login", "test");
        json.put("password", "11111");

        Mockito.doReturn(null)
                .when(postRequester)
                .doPost(Mockito.any(), Mockito.any());

        try {
            authService.getUserKeys(json);
            Assertions.fail("Expected RuntimeException");
        } catch (RuntimeException thrown) {
            Assertions.assertEquals("CONNECTION_REFUSED. " + ErrorCode.CONNECTION_REFUSED.getMessage(), thrown.getMessage());
        }
    }
}
