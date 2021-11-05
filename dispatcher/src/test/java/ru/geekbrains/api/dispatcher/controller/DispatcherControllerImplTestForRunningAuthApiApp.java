package ru.geekbrains.api.dispatcher.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.geekbrains.api.dispatcher.exception.DispatcherApiException;
import ru.geekbrains.api.dispatcher.exception.ErrorCode;

@SpringBootTest
public class DispatcherControllerImplTestForRunningAuthApiApp {

    @Autowired
    DispatcherControllerImpl dispatcherController;

    @Test
    void internalErrorTest() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();
        json.put("login", "test");
        json.put("email", "test@gmail.com");
        json.put("password", 11111);

        try {
            dispatcherController.registerUser(json);
            Assertions.fail("Expected RuntimeException");
        } catch (DispatcherApiException exception) {
            Assertions.assertEquals("INTERNAL_ERROR. " + ErrorCode.INTERNAL_ERROR.getMessage(),
                    exception.getMessage());
        }
    }

    @Test
    void registerUserTest() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();
        json.put("login", "test");
        json.put("email", "test@gmail.com");
        json.put("password", "11111");
        ResponseEntity<?> responseEntity = dispatcherController.registerUser(json);
        Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCode().value());
        Assertions.assertTrue(responseEntity.hasBody());

        JsonNode checkJson = (JsonNode) responseEntity.getBody();
        JsonNode reportJson = checkJson.path("report");
        Assertions.assertTrue(reportJson.has("key"));
        Assertions.assertTrue(reportJson.hasNonNull("key"));
        Assertions.assertTrue(reportJson.path("key").isTextual());
    }
}
