package ru.geekbrains.api.dispatcher.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.geekbrains.api.dispatcher.config.RestTemplateConfig;

@SpringBootTest(classes = {PostRequester.class, RestTemplateConfig.class})
public class PostRequesterTest {

    @Autowired
    PostRequester postRequester;

    @Test
    void resourceAccessExceptionTest() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();
        json.put("login", "test");
        json.put("password", "11111");
        String url = "http://localhost:8092/api";
        Assertions.assertNull(postRequester.doPost(json, url));
    }
}
