package ru.geekbrains.api.data_api.services;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Value("${authApi.register}")
    private String url;

    private final RestService restService;

    @Autowired
    public AuthService(RestService restService) {
        this.restService = restService;
    }

    public ResponseEntity<?> registerUser(ObjectNode json){
        return restService.doPost(json, url);
    }
}
