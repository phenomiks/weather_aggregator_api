package ru.geekbrains.api.data_api.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.geekbrains.api.data_api.application.exception.DataApiException;
import ru.geekbrains.api.data_api.application.exception.ErrorCode;
import ru.geekbrains.api.data_api.request.DataParameters;

@Service
public class LoaderService {

    @Value("${loader.getWeather}")
    private String url;

    private final RestService restService;

    @Autowired
    public LoaderService(RestService restService) {
        this.restService = restService;
    }

    public ResponseEntity<ObjectNode> loaderWeather(DataParameters dataParameters){
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.valueToTree(dataParameters);
        ResponseEntity<ObjectNode> responseEntity = restService.doPost(jsonNode, url);
        if (responseEntity == null){
            throw new DataApiException("Connection time out ",
                    ErrorCode.CONNECTION_REFUSED,
                    "Connection time out");
        }
        return responseEntity;
    }
}
