package ru.geekbrains.api.dispatcher.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.geekbrains.api.dispatcher.application.utils.ExceptionHandlerForPostRequester;

@Service
public class AuthService {

    @Value("${authApi.register}")
    private String urlRegister;

    private final PostRequester postRequester;

    @Autowired
    public AuthService(PostRequester postRequester) {
        this.postRequester = postRequester;
    }

    public ResponseEntity<?> registerUser(ObjectNode json){
        ResponseEntity<?> responseEntity = postRequester.doPost(json, urlRegister);
        if (responseEntity == null){
            ExceptionHandlerForPostRequester.timeOutException();
        }
        return responseEntity;
    }
}
