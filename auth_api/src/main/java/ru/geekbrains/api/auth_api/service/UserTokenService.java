package ru.geekbrains.api.auth_api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.api.auth_api.application.utils.JsonResponseGenerator;
import ru.geekbrains.api.auth_api.application.utils.JwtTokenUtil;
import ru.geekbrains.api.auth_api.model.User;
import ru.geekbrains.api.auth_api.model.request.UserRegParams;
import ru.geekbrains.api.auth_api.service.interfaces.UserServiceFacade;

import java.util.Optional;

@Service
public class UserTokenService implements UserServiceFacade {
    private final UserServiceImpl userService;
    private final TokenServiceImpl tokenService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public UserTokenService(UserServiceImpl userService, TokenServiceImpl tokenService,
                            JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public ObjectNode generateKeyResponse(UserRegParams regParams) {
        Optional<User> optionalUser = userService.findByLogin(regParams.getLogin());
        User user = (optionalUser.isEmpty())
                ? userService.saveUser(regParams.getLogin(), regParams.getEmail(), regParams.getPassword())
                : optionalUser.get();

        String token = jwtTokenUtil.generateToken(user.getLogin());

        tokenService.saveToken(user, token);

        return JsonResponseGenerator.generateReportResponseJson(generateKeyReport(token));
    }

    private ObjectNode generateKeyReport(String token) {
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode report = mapper.createObjectNode();
        report.put("key", token);

        return report;
    }
}
