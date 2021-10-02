package ru.geekbrains.api.auth_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.api.auth_api.model.User;
import ru.geekbrains.api.auth_api.repository.UserRepository;
import ru.geekbrains.api.auth_api.service.interfaces.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }

    @Override
    public User saveUser(String login, String email, char[] password) {
        User user = new User(login, email, password);

        return userRepository.save(user);
    }
}
