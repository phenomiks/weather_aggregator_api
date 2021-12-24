package ru.geekbrains.api.auth_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.api.auth_api.model.Token;

import java.util.Set;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Set<Token> findAllByUserId(Long userId);
}
