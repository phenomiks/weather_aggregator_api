package ru.geekbrains.api.auth_api.controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import ru.geekbrains.api.auth_api.exception.ErrorCode;
import ru.geekbrains.api.auth_api.model.request.UserRegParams;
import ru.geekbrains.api.auth_api.service.interfaces.UserService;
import ru.geekbrains.api.auth_api.service.interfaces.UserServiceFacade;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class AuthControllerImplTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    UserServiceFacade userTokenService;

    @Autowired
    UserService userService;

    @Test
    @Order(1)
    void jsonErrorTest() throws Exception {
        String json = "{\n" +
                "    \"login\": \"test\",\n" +
                "    \"email\": \"test@gmail.com\",\n" +
                "    \"password\": \"qwerty\"\n";

        mvc.perform(
                        post("/api/v1/auth/register")
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
    @Order(2)
    void jsonValidationErrorTest() throws Exception {
        String json = "{\n" +
                "    \"login\": \"test\",\n" +
                "    \"email\": \"test@gmailcom\",\n" +
                "    \"password\": \"qwerty\"\n" +
                "}";

        mvc.perform(
                        post("/api/v1/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.error.errorCode").exists())
                .andExpect(jsonPath("$.error.errorCode", is(ErrorCode.JSON_VALIDATION_ERROR.toString())))
                .andExpect(jsonPath("$.error.message", is("The email 'test@gmailcom' is not valid")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Order(4)
    void registerUserTest() throws Exception {
        String json = "{\n" +
                "    \"login\": \"test\",\n" +
                "    \"email\": \"test@gmail.com\",\n" +
                "    \"password\": \"qwerty\"\n" +
                "}";

        mvc.perform(
                        post("/api/v1/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status", is("report")))
                .andExpect(jsonPath("$.report.key").exists())
                .andExpect(jsonPath("$.report.key").isString())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Order(3)
    void userNotFoundTest() throws Exception {
        String json = "{\n" +
                "    \"login\": \"test\",\n" +
                "    \"password\": \"qwerty\"\n" +
                "}";

        mvc.perform(
                        post("/api/v1/auth/new-key")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.error.errorCode").exists())
                .andExpect(jsonPath("$.error.errorCode", is(ErrorCode.USER_NOT_FOUND.toString())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Order(5)
    void getNewKeyTest() throws Exception {
        String json = "{\n" +
                "    \"login\": \"test2\",\n" +
                "    \"password\": \"qwerty\"\n" +
                "}";

        userService.saveUser("test2", "test2@gmail.com", "qwerty".toCharArray());

        mvc.perform(
                        post("/api/v1/auth/new-key")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status", is("report")))
                .andExpect(jsonPath("$.report.key").exists())
                .andExpect(jsonPath("$.report.key").isString())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Order(6)
    void getUserKeysTest() throws Exception {
        String json = "{\n" +
                "    \"login\": \"test3\",\n" +
                "    \"password\": \"qwerty\"\n" +
                "}";

        userTokenService.generateKeyResponse(
                new UserRegParams("test3", "qwerty".toCharArray(), "test3@gmail.com")
        );

        mvc.perform(
                        post("/api/v1/auth/user-keys")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status").value("report"))
                .andExpect(jsonPath("$.report.keys").exists())
                .andExpect(jsonPath("$.report.keys").isArray())
                .andExpect(jsonPath("$.report.keys", hasSize(1)))
                .andExpect(jsonPath("$.report.keys[0]").isString())
                .andDo(MockMvcResultHandlers.print());
    }
}
