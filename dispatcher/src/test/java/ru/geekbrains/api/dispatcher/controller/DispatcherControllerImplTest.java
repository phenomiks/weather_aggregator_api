package ru.geekbrains.api.dispatcher.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;;
import ru.geekbrains.api.dispatcher.exception.DispatcherApiException;
import ru.geekbrains.api.dispatcher.exception.ErrorCode;
import ru.geekbrains.api.dispatcher.service.PostRequester;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DispatcherControllerImplTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    DispatcherControllerImpl dispatcherController;

    @MockBean
    PostRequester postRequester;

    @Test
    void jsonErrorTest() throws Exception {
        String json = "{\n" +
                "    \"login\": \"test\",\n" +
                "    \"email\": \"test@gmail.com\",\n" +
                "    \"password\": \"qwerty\"\n";

        mvc.perform(
                        post("/api/v1/dispatcher/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.error.errorCode").exists())
                .andExpect(jsonPath("$.error.errorCode", is(ErrorCode.JSON_ERROR.toString())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getWeatherStringKeyTest() {
        int number = 111111;
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();
        json.put("city", "moscow");
        json.put("services", "openweather");
        json.put("key", number);

        try {
            dispatcherController.getWeather(json);
            Assertions.fail("Expected RuntimeException");
        } catch (DispatcherApiException exception) {
            Assertions.assertEquals("JSON_VALIDATION_ERROR. " + number + " value must be a string",
                    exception.getMessage());
        }
    }

    @Test
    void getWeatherNoKeyTest() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();
        json.put("city", "moscow");
        json.put("services", "openweather");

        try {
            dispatcherController.getWeather(json);
            Assertions.fail("Expected RuntimeException");
        } catch (DispatcherApiException exception) {
            Assertions.assertEquals("JSON_VALIDATION_ERROR. Not found key field", exception.getMessage());
        }
    }
}
