package ru.geekbrains.api.auth_api.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.geekbrains.api.auth_api.model.User;
import ru.geekbrains.api.auth_api.repository.UserRepository;

import java.util.Optional;

@SpringBootTest(classes = UserServiceImpl.class)
@ActiveProfiles("test")
class UserServiceImplTests {
    @Autowired
    UserServiceImpl userService;

    @MockBean
    UserRepository userRepository;

    User userFromDB;

    @BeforeEach
    public void init() {
        userFromDB = new User();
        userFromDB.setId(5L);
        userFromDB.setLogin("testUser");
        userFromDB.setEmail("testUser@gmail.com");
        userFromDB.setPassword("$2y$12$tB5v".toCharArray());
    }

    @Test
    void findByLoginTest() {
        Mockito.doReturn(Optional.of(userFromDB))
                .when(userRepository)
                .findUserByLogin(userFromDB.getLogin());

        Optional<User> user = userService.findByLogin(userFromDB.getLogin());

        Mockito.verify(userRepository, Mockito.times(1))
                .findUserByLogin(userFromDB.getLogin());

        Assertions.assertNotNull(user);
        Assertions.assertTrue(user.isPresent());
        Assertions.assertEquals(userFromDB.getEmail(), user.get().getEmail());
    }

    @Test
    void findByLoginOrEmailTest() {
        Mockito.doReturn(Optional.of(userFromDB))
                .when(userRepository)
                .findUserByLoginOrEmail(userFromDB.getLogin(), userFromDB.getEmail());

        Optional<User> user = userService.findByLoginOrEmail(userFromDB.getLogin(), userFromDB.getEmail());

        Mockito.verify(userRepository, Mockito.times(1))
                .findUserByLoginOrEmail(userFromDB.getLogin(), userFromDB.getEmail());

        Assertions.assertNotNull(user);
        Assertions.assertTrue(user.isPresent());
        Assertions.assertEquals(userFromDB.getLogin(), user.get().getLogin());
    }

    @Test
    void saveUserTest() {
        Mockito.doReturn(userFromDB)
                .when(userRepository)
                .save(Mockito.any(User.class));

        User user = userService.saveUser(userFromDB.getLogin(), userFromDB.getEmail(), userFromDB.getPassword());

        Mockito.verify(userRepository, Mockito.times(1))
                .save(Mockito.any(User.class));

        Assertions.assertNotNull(user);
        Assertions.assertEquals(userFromDB.getEmail(), user.getEmail());
    }
}
