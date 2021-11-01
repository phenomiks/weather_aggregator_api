package ru.geekbrains.api.dispatcher.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.geekbrains.api.dispatcher.exception.ErrorCode;
import ru.geekbrains.api.dispatcher.utils.TokenValidator;



@SpringBootTest(classes = DataService.class)
public class DataServiceTest {

    @Autowired
    DataService dataService;

    @MockBean
    PostRequester postRequester;

    @MockBean
    TokenValidator tokenValidator;

    ObjectNode json;
    String key;

    @BeforeEach
    void init() {
        ObjectMapper mapper = new ObjectMapper();
        json = mapper.createObjectNode();
        json.put("city", "moscow");
        json.put("services", "openweather");
        json.put("key", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNjM0NTY0MDM0LCJ" +
                "leHAiOjE2MzQ1NjQwOTR9.cxgXPW5LpZGTfrI1wnEhLEofF7UBka7dKPhBSu7P0-4");
        key = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNjM0NTY0MDM0LCJ" +
                "leHAiOjE2MzQ1NjQwOTR9.cxgXPW5LpZGTfrI1wnEhLEofF7UBka7dKPhBSu7P0-4";
    }

    @Test
    void getWeatherTimeOutExceptionTest() {
        Mockito.doReturn(null)
                .when(postRequester)
                .doPost(Mockito.any(), Mockito.any());

        try {
            dataService.getWeather(json, key);
            Assertions.fail("Expected RuntimeException");
        } catch (RuntimeException exception) {
            Assertions.assertEquals("CONNECTION_REFUSED. " + ErrorCode.CONNECTION_REFUSED.getMessage(),
                    exception.getMessage());
        }
    }

    @Test
    void getWeatherRemoveKeyTest() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        response.put("weather", "ok");
        ResponseEntity responseEntity = new ResponseEntity(response, HttpStatus.valueOf(200));

        Mockito.doReturn(responseEntity)
                .when(postRequester)
                .doPost(Mockito.any(), Mockito.any());

        dataService.getWeather(json, key);

        ArgumentCaptor requestCaptor = ArgumentCaptor.forClass(ObjectNode.class);
        Mockito.verify(postRequester, Mockito.times(1))
                .doPost((ObjectNode) requestCaptor.capture(), Mockito.any());
        ObjectNode capturedArgument = (ObjectNode) requestCaptor.getValue();
        Assertions.assertFalse(capturedArgument.has("key"));
    }
}
